package pl.jdev.opes.db.dto.mapper;

import org.springframework.stereotype.Component;
import pl.jdev.opes.db.dto.TagDto;
import pl.jdev.opes_commons.db.AbstractMapper;

@Component
public class TagMapper extends AbstractMapper<String, TagDto> {
    @Override
    public TagDto convertToDto(String tag) {
//        mapper.createTypeMap(String.class, TagDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(tag, TagDto::setTag);
//                });
        TagDto dto = new TagDto(tag);
        return dto;
    }

    @Override
    public String convertToEntity(TagDto tagDto) {
        return tagDto.getTag();
    }
}
