package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Loader;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Price")
@Table(name = "prices")
@Loader(namedQuery = "findPriceById")
@NamedQuery(name = "findPriceById",
        query = "SELECT p " +
                "FROM Price p " +
                "WHERE p.id = ?1")
public class PriceDto extends AuditDto {
    private static final long serialVersionUID = -6049942254036570239L;
    @EmbeddedId
    private PriceCompositeKey priceId;
    @Column
    private Double price;
    @Column
    private String source;
}
