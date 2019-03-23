package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import pl.jdev.opes.db.dto.metadata.Taggable;
import pl.jdev.opes_commons.domain.order.OrderState;
import pl.jdev.opes_commons.domain.order.OrderType;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity(name = "Order")
@Table(name = "orders")
@SQLDelete(sql = "UPDATE order " +
        "SET deletedAt = CURRENT_TIMESTAMP " +
        "WHERE id = ?")
//@Loader(namedQuery = "findOrderById")
//@NamedQuery(name = "findOrderById",
//        query = "SELECT o " +
//                "FROM Order o " +
//                "WHERE o.id = ?1 " +
//                "AND o.deletedAt IS NULL")
//@Where(clause = "deletedAt IS NULL")
public class OrderDto extends DeletableAuditDto implements Taggable {
    private static final long serialVersionUID = 3004071548855174997L;
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String extId;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderState state;
    @ManyToOne(fetch = FetchType.EAGER)
    private AccountDto account;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderType type;
    @Column
    private Double units;
    @ManyToOne
    @JoinColumn
    private InstrumentDto instrument;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_comment",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentDto> comments = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_tag",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagDto> tags = new HashSet<>();
}
