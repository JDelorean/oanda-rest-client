package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.jdev.opes_commons.domain.order.OrderState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Order")
@Table(name = "order")
@SQLDelete(sql =
        "UPDATE order " +
                "SET deletedAt = CURRENT_TIMESTAMP " +
                "WHERE id = ?")
@Loader(namedQuery = "findTagById")
@NamedQuery(name = "findTagById", query =
        "SELECT o " +
                "FROM Order o " +
                "WHERE o.id = ?1 " +
                "AND o.deletedAt IS NULL")
@Where(clause = "deletedAt IS NULL")
public class OrderDto extends AuditDto {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String extId;
    @Column
    private Date extCreateTime;
    @Column
    private OrderState state;
    @ManyToMany
    @JoinTable(name = "order_comment",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentDto> comments = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "order_tag",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagDto> tags = new ArrayList<>();
}
