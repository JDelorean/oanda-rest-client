package pl.jdev.opes_abacus.controller.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MAInputArgs.class, name = "SMA"),
        @JsonSubTypes.Type(value = MAInputArgs.class, name = "EMA")
})
public interface InputArgs {
}
