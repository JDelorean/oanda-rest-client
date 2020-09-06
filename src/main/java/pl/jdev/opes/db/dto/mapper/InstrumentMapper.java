package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.InstrumentDto;
import pl.jdev.opes_commons.db.AbstractMapper;
import pl.jdev.opes_commons.domain.instrument.Instrument;

@Component
public class InstrumentMapper extends AbstractMapper<Instrument, InstrumentDto> {
    @Autowired
    private WebApplicationContext context;

    @Override
    public Instrument convertToEntity(InstrumentDto instrumentDto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        return mapper.map(instrumentDto, Instrument.class);
    }

    @Override
    public InstrumentDto convertToDto(Instrument entity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        return mapper.map(entity, InstrumentDto.class);
    }
}
