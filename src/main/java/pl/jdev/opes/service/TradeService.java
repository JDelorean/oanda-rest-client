package pl.jdev.opes.service;

import org.springframework.stereotype.Service;
import pl.jdev.opes.db.dto.TradeDto;

import java.util.UUID;

@Service
public class TradeService extends TaggableEntityService<TradeDto, UUID> {
}
