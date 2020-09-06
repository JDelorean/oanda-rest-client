package pl.jdev.opes_abacus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdev.opes_abacus.controller.dto.CalculationType;
import pl.jdev.opes_abacus.db.dto.ResultDto;
import pl.jdev.opes_abacus.db.dto.mapper.ResultMapper;
import pl.jdev.opes_abacus.db.repo.ResultRepository;
import pl.jdev.opes_abacus.domain.Result;
import pl.jdev.opes_commons.rest.exception.NotFoundException;

import java.util.UUID;

@Service
@Transactional
public class ResultManagerImpl implements ResultManager {

    private ResultRepository resultRepository;
    private ResultMapper resultMapper;

    public ResultManagerImpl(final ResultRepository resultRepository, final ResultMapper resultMapper) {
        this.resultRepository = resultRepository;
        this.resultMapper = resultMapper;
    }

    @Override
    public Result getResult(UUID resultId) throws NotFoundException {
        ResultDto resultDto = resultRepository.findById(resultId).orElseThrow(NotFoundException::new);
        return resultMapper.convertToEntity(resultDto);
    }

    @Override
    public Result storeResult(Object[] result) {
        return null;
    }

    @Override
    public Result storeResult(CalculationType type, Object[] result) {
        ResultDto dto = resultMapper.convertToDto(new Result(type, result));
        return resultMapper.convertToEntity(resultRepository.save(dto));
    }
}
