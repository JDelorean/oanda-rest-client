package pl.jdev.opes_abacus.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import pl.jdev.opes_abacus.controller.dto.CalculationType;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Result")
@Table(name = "results")
@SQLDelete(sql = "UPDATE  result " +
        "SET deletedAt = CURRENT_TIMESTAMP " +
        "WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;
    private CalculationType type;
    private Object[] values;
}
