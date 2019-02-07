package pl.jdev.opes.db.dto.metadata;

import pl.jdev.opes.db.dto.TagDto;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

public interface Taggable extends MetaDatable<TagDto> {
    default void tagWith(String tag) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("Im here now!");
        Optional<Field> tagsFieldOptional = getMetadataField();
//        tagsFieldOptional.orElseThrow(NoSuchFieldException::new);
//        tagsField.setAccessible(true);
        Set<TagDto> tags = (Set<TagDto>) tagsFieldOptional
                .orElseThrow(NoSuchFieldException::new)
                .get(this);
        tags.add(new TagDto(tag));
    }

    default void removeTag(String tag) throws NoSuchFieldException, IllegalAccessException {
        Optional<Field> tagsFieldOptional = getMetadataField();
        Set<TagDto> tags = (Set<TagDto>) tagsFieldOptional
                .orElseThrow(NoSuchFieldException::new)
                .get(this);
        tags.remove(new TagDto(tag));
    }
}
