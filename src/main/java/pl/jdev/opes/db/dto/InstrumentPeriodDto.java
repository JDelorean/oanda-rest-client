package pl.jdev.opes.db.dto;

import pl.jdev.opes.db.dto.metadata.Commentable;
import pl.jdev.opes_commons.db.AuditDto;

import javax.persistence.*;
import java.util.Date;

//@Getter
//@Entity(name = "InstrumentPeriod")
//@Table(name = "instrumentPeriods")
//@NoArgsConstructor
public class InstrumentPeriodDto extends AuditDto implements Commentable {
    private static final long serialVersionUID = 5416785232794291406L;
    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startsAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endsAt;
    @Column(nullable = false)
    private PeriodType type;

}
