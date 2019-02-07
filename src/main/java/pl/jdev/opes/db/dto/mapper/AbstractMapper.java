package pl.jdev.opes.db.dto.mapper;

abstract class AbstractMapper<Object, AuditDto> {
    public abstract Object convertToEntity(AuditDto dto);

    public abstract AuditDto convertToDto(Object entity);
}