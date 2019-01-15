package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Tag")
@Table(name = "tag")
@SQLDelete(sql =
        "UPDATE tag " +
                "SET deletedAt = CURRENT_TIMESTAMP " +
                "WHERE id = ?")
@Loader(namedQuery = "findTagById")
@NamedQuery(name = "findTagById", query =
        "SELECT t " +
                "FROM Tag t " +
                "WHERE t.id = ?1 " +
                "AND t.deletedAt IS NULL")
@Where(clause = "deletedAt IS NULL")
public class TagDto extends AuditDto {
    @Id
    private int id;
    @Column
    private String tag;
}
