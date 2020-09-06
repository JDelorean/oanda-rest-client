package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import pl.jdev.opes.db.dto.metadata.Taggable;
import pl.jdev.opes_commons.db.DeletableAuditDto;
import pl.jdev.opes_commons.domain.trade.TradeState;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity(name = "Trade")
@Table(name = "trades")
@SQLDelete(sql = "UPDATE trade " +
        "SET deletedAt = CURRENT_TIMESTAMP " +
        "WHERE id = ?")
//@Loader(namedQuery = "findTradeById")
//@NamedQuery(name = "findTradeById",
//        query = "SELECT t " +
//                "FROM Trade t " +
//                "WHERE t.id = ?1 " +
//                "AND t.deletedAt IS NULL")
//@Where(clause = "deletedAt IS NULL")
public class TradeDto extends DeletableAuditDto implements Taggable {
    private static final long serialVersionUID = -9185597420221395918L;
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String extId;
    @ManyToOne(fetch = FetchType.EAGER)
    private AccountDto account;
    @Column
    private Double price;
    @Column
    private Date openTime;
    @Column
    private Date closeTime;
    @Column
    @Enumerated(EnumType.STRING)
    private TradeState state;
    @ManyToOne
    @JoinColumn
    private InstrumentDto instrument;
    @Column
    private Double initialUnits;
    @Column
    private Double initialMarginRequired;
    @Column
    private Double currentUnits;
    @Column
    private Double realizedPL;
    @Column
    private Double unrealizedPL;
    @Column
    private Double averageClosePrice;
    //    @Column
//    private List<String> closingTransactionIDs;
    @Column
    private Double financing;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trade_comment",
            joinColumns = @JoinColumn(name = "trade_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentDto> comments = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trade_tag",
            joinColumns = @JoinColumn(name = "trade_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagDto> tags = new HashSet<>();
}
