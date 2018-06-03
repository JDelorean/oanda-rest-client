package pl.jdev.oanda_rest_client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = "documentId")
public abstract class AbstractEntity {
    @Id
    ObjectId documentId;
    @CreatedDate
    LocalDateTime createdTime;
    @LastModifiedDate
    LocalDateTime modifiedDate;
}
