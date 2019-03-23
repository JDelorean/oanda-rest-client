package pl.jdev.opes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdev.opes.db.dto.TradeDto;
import pl.jdev.opes.db.dto.mapper.TradeMapper;
import pl.jdev.opes.db.repo.TradeRepository;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.trade.Trade;
import pl.jdev.opes_commons.domain.trade.TradeState;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.IntegrationClient;
import pl.jdev.opes_commons.rest.json.CustomJsonBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static pl.jdev.opes_commons.rest.HttpHeaders.REQUEST_TYPE;
import static pl.jdev.opes_commons.rest.message.event.EventType.TRADE_UPDATED;
import static pl.jdev.opes_commons.rest.message.request.RequestType.CLOSE_TRADE;
import static pl.jdev.opes_commons.rest.message.request.RequestType.TRADE_DETAILS;

@Service
public class TradeService extends TaggableEntityService<TradeDto, UUID> {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private IntegrationClient intC;


    public Set<Trade> getAllTrades(TradeState state) {
        List<TradeDto> dtos = tradeRepository.findAll();
        return dtos.stream()
                .map(tradeMapper::convertToEntity)
                .filter(trade -> state.equals(null) ? true : trade.getState() == state)
                .collect(Collectors.toSet());
    }

    public Trade getTrade(UUID tradeId) throws NotFoundException {
        TradeDto dto = tradeRepository.findById(tradeId).orElseThrow(NotFoundException::new);
        return tradeMapper.convertToEntity(dto);
    }

    public void closeTrade(UUID tradeId) throws NotFoundException {
        TradeDto dto = tradeRepository.findById(tradeId).orElseThrow(NotFoundException::new);
        Trade trade = tradeMapper.convertToEntity(dto);
        String payload = "";
        try {
            payload = new CustomJsonBuilder(ctx.getBean(ObjectMapper.class))
                    .replace("accountId", dto.getAccount().getExtId())
                    .buildForObject(trade);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = MessageBuilder
                .withPayload(payload)
                .setHeader(REQUEST_TYPE, CLOSE_TRADE)
                .build();
        intC.request(msg);
    }

    @Transactional
    public Trade updateTrade(Trade tradeUpdate) {
        TradeDto dto = tradeRepository.findByExtId(tradeUpdate.getExtId()).orElseThrow(NotFoundException::new);
        tradeUpdate.setAccountId(dto.getAccount().getId());
        return tradeMapper.convertToEntity(tradeRepository.save(tradeMapper.convertToDto(tradeUpdate)));
    }

    @Transactional
    void syncTradesForAccount(Account account) {
        Trade dummy = new Trade();
        String payload = "";
        try {
            payload = new CustomJsonBuilder(ctx.getBean(ObjectMapper.class))
                    .replace("accountId", account.getExtId())
                    .buildForObject(dummy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = MessageBuilder
                .withPayload(payload)
                .setHeader(REQUEST_TYPE, TRADE_DETAILS)
                .build();
        Arrays.stream((Trade[]) intC
                .request(msg, Trade[].class)
                .getBody())
                .peek(trade -> trade.setAccountId(account.getId()))
                .map(tradeMapper::convertToDto)
                .forEach(tradeRepository::save);
    }

    private Optional<Trade> getTradeDetailsFromExternal(Trade trade) {
        String payload = "";
        try {
            payload = new CustomJsonBuilder(ctx.getBean(ObjectMapper.class))
                    .replace("accountId", accountService.getAccount(trade.getAccountId()).getExtId())
                    .buildForObject(trade);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = MessageBuilder.withPayload(payload)
                .setHeader(REQUEST_TYPE, TRADE_DETAILS)
//TODO:         .setHeader(HttpHeaders.SOURCE, trade.)
                .build();
        trade = (Trade) intC.request(msg, Trade.class)
                .getBody();
        return Optional.ofNullable(trade);
    }

    @Scheduled(fixedRateString = "${opes.poll.trade}")
    void pollTrades() {
        Map<UUID, Trade> trades = tradeRepository.findAll()
                .parallelStream()
                .filter(trade -> trade.getAccount().isSynced())
                .map(tradeMapper::convertToEntity)
                .collect(Collectors.toMap(Trade::getId, trade -> trade));
        trades.values()
                .parallelStream()
                .map(this::getTradeDetailsFromExternal)
                .map(Optional::get)
                .map(trade -> {
                    UUID tradeId = tradeRepository
                            .findByExtId(trade.getExtId())
                            .get()
                            .getId();
                    trade.setId(tradeId);
                    return trade;
                })
                .filter(trade -> !trades.get(trade.getId()).equals(trade))
                .map(this::updateTrade)
                .map(trade -> MessageBuilder
                        .withPayload(trade)
                        .setHeader(HttpHeaders.EVENT_TYPE, TRADE_UPDATED)
                        .build()
                )
                .forEach(intC::post);
    }
}
