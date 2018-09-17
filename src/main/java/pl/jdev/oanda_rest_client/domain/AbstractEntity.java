package pl.jdev.oanda_rest_client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = "_id")
@Document
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class AbstractEntity implements Persistable<ObjectId> {
    @JsonIgnore
    @Id
    ObjectId _id;
    @JsonIgnore
    @CreatedDate
    Date _createdDate;
    @JsonIgnore
    @LastModifiedDate
    Date _modifiedDate;
    @JsonIgnore
    @Transient
    private boolean _persisted;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return _persisted;
    }

    @Override
    public ObjectId getId() {
        return this._id;
    }
}
