package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Comment")
@Table(name = "comments")
@SQLDelete(sql =
        "UPDATE comment " +
                "SET deletedAt = CURRENT_TIMESTAMP " +
                "WHERE id = ?")
//@Loader(namedQuery = "findCommentById")
//@NamedQuery(name = "findCommentById", query =
//        "SELECT c " +
//                "FROM Comment c " +
//                "WHERE c.id = ?1 " +
//                "AND c.deletedAt IS NULL")
//@Where(clause = "deletedAt IS NULL")
public class CommentDto extends DeletableAuditDto {
    private static final long serialVersionUID = 3022397426017151900L;
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String comment;
}
