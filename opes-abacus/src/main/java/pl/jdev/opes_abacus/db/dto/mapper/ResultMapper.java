package pl.jdev.opes_abacus.db.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes_abacus.db.dto.ResultDto;
import pl.jdev.opes_abacus.domain.Result;
import pl.jdev.opes_commons.db.AbstractMapper;

@Component
public class ResultMapper extends AbstractMapper<Result, ResultDto> {
    @Autowired
    private WebApplicationContext context;

    @Override
    public Result convertToEntity(ResultDto resultDto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        return mapper.map(resultDto, Result.class);
    }

    @Override
    public ResultDto convertToDto(Result entity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        return mapper.map(entity, ResultDto.class);
    }
}
