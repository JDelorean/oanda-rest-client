package pl.jdev.opes.db.dto;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import pl.jdev.opes.db.dto.metadata.Taggable;
import pl.jdev.opes_commons.db.AuditDto;
import pl.jdev.opes_commons.domain.instrument.InstrumentType;

import javax.persistence.*;

@Getter
@Entity(name = "Instrument")
@Table(name = "instruments")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class InstrumentDto extends AuditDto implements Taggable {
    private static final long serialVersionUID = 323433931446474834L;
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @Column(unique = true, nullable = false, updatable = false)
    private String name;
    @NonNull
    @Column
    private String description;
    @NonNull
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private InstrumentType type;
    @NonNull
    @Column
    @ColumnDefault("false")
    private Boolean isTradable;
    @NonNull
    @Column
    @ColumnDefault("false")
    private Boolean isTracked;
}
