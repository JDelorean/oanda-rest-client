package pl.jdev.opes.db.dto.metadata;

import com.google.common.reflect.TypeToken;
import pl.jdev.opes_commons.db.AuditDto;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

interface MetaDatable<T extends AuditDto> {
    default Optional<Field> getMetadataField() {
        TypeToken<T> typeToken = new TypeToken<T>(this.getClass()) {
        };
        Type type = typeToken.getType();
//        Arrays.asList(T.class.getTypeParameters()).forEach(System.out::println);
//        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getTypeParameters()[0];
//        Class paramTypeClass = parameterizedType.getClass();
//        Optional<Boolean> hasTagField = Optional.of(hasFieldParameterizedWith(paramTypeClass));
//        hasTagField.orElseThrow(NoSuchFieldException::new);
        return Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(Collection.class))
                .filter(field -> field.getType().getTypeParameters().length != 0)
                .filter(field -> Arrays.stream(field.getType().getTypeParameters())
                        .anyMatch(typeVar -> typeVar.getClass().isInstance(type)))
                .findFirst();
    }

    private boolean hasFieldParameterizedWith(Class clazz) throws IllegalStateException {
        long tagFieldsAmt = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(Collection.class))
                .filter(field -> field.getType().getTypeParameters().length != 0)
                .filter(field -> Arrays.stream(field.getType().getTypeParameters())
                        .anyMatch(typeVar -> typeVar.getClass().isInstance(clazz)))
                .count();
        if (tagFieldsAmt > 1)
            throw new IllegalStateException(String.format("Found %s fields, expecting just 1!", tagFieldsAmt));
        if (tagFieldsAmt == 1) return true;
        return false;
    }
}
