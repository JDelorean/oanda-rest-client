package pl.jdev.opes.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.domain.pricing.Price;
import pl.jdev.opes.rest.json.wrapper.JsonPricingListWrapper;
import pl.jdev.opes.service.oanda_service.pricing.OandaPricingService;

import javax.validation.Valid;

import static org.springframework.util.CollectionUtils.arrayToList;

@RestController
@RequestMapping("/accounts/{accountId}/pricing")
@Log4j2
public class PricingController extends AbstractEntityController<Price> {
    @Autowired
    OandaPricingService oandaPricingService;

    @GetMapping
    @ResponseBody
    public JsonPricingListWrapper getPrices(@Valid @PathVariable(name = "accountId") final String accountId,
                                            @RequestParam(value = "instruments") final String instruments,
                                            @RequestParam(value = "since", required = false) final String since,
                                            @RequestParam(value = "includeUnitsAvailable", required = false) final boolean includeUnitsAvailable,
                                            @RequestParam(value = "includeHomeConversions", required = false) final boolean includeHomeConversions) {
        return JsonPricingListWrapper.payloadOf(oandaPricingService.getPrices(accountId,
                arrayToList(instruments.split(",")),
                since,
                includeUnitsAvailable,
                includeHomeConversions));
    }
}
