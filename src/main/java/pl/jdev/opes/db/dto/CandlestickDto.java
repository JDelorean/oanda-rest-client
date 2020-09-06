package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.jdev.opes_commons.db.AuditDto;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Candlestick")
@Table(name = "candles")
@NoArgsConstructor
@ToString
public class CandlestickDto extends AuditDto {
    private static final long serialVersionUID = -7376340724436187964L;
    @EmbeddedId
    private CandlestickCompositeKey id;
    @Column
    @Enumerated(EnumType.STRING)
    private CandlestickSource source;
    @Column
    @Enumerated(EnumType.STRING)
    private CandlestickPriceType type;
    @Column
    private Double open;
    @Column
    private Double high;
    @Column
    private Double low;
    @Column
    private Double close;
}
