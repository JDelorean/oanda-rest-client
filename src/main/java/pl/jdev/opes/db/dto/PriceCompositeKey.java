package pl.jdev.opes.db.dto;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Embeddable
public class PriceCompositeKey implements Serializable {
    private static final long serialVersionUID = 1821263262703071223L;
    @Column(length = 4)
    private String instrument;
    @Temporal(TemporalType.TIMESTAMP)
    private Date priceAt;
}
