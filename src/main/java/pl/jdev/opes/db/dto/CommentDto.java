package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Comment")
@Table(name = "comment")
@SQLDelete(sql =
        "UPDATE comment " +
                "SET deletedAt = CURRENT_TIMESTAMP " +
                "WHERE id = ?")
@Loader(namedQuery = "findCommentById")
@NamedQuery(name = "findCommentById", query =
        "SELECT c " +
                "FROM Comment c " +
                "WHERE c.id = ?1 " +
                "AND c.deletedAt IS NULL")
@Where(clause = "deletedAt IS NULL")
public class CommentDto extends AuditDto {
    @Id
    private int id;
    @Column
    private String comment;
}
