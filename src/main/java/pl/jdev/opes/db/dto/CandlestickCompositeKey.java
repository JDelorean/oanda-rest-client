package pl.jdev.opes.db.dto;

import lombok.*;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ToString
public class CandlestickCompositeKey implements Serializable {
    private static final long serialVersionUID = -2097818953948483383L;
    @ManyToOne
    @JoinColumn
    private InstrumentDto instrument;
    @Column
    @Enumerated(EnumType.STRING)
    private CandlestickGranularity granularity;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}