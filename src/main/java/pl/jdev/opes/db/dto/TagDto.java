package pl.jdev.opes.db.dto;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import pl.jdev.opes_commons.db.DeletableAuditDto;

import javax.persistence.*;

@Entity(name = "Tag")
@Table(name = "tags")
@SQLDelete(sql =
        "UPDATE tag " +
                "SET deletedAt = CURRENT_TIMESTAMP " +
                "WHERE id = ?")
//@Loader(namedQuery = "findTagById")
//@NamedQuery(name = "findTagById", query =
//        "SELECT t " +
//                "FROM Tag t " +
//                "WHERE t.id = ?1 " +
//                "AND t.deletedAt IS NULL")
//@Where(clause = "deletedAt IS NULL")
@RequiredArgsConstructor
@EqualsAndHashCode
public class TagDto extends DeletableAuditDto {
    private static final long serialVersionUID = 6181783680357743559L;
    @Id
    @GeneratedValue
    @Getter
    private Long id;
    @Column(unique = true)
    @NonNull
    @Getter
    @Setter
    private String tag;
}
