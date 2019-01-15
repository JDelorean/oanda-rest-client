package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

abstract class EntityDtoMapper<Object, AuditDto> {
    @Autowired
    ModelMapper modelMapper;

    public abstract AuditDto convertToDto(Object entity);

    public abstract Object convertToEntity(AuditDto dto);
}