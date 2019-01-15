package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.jdev.opes_commons.domain.ClientExtensions;
import pl.jdev.opes_commons.domain.trade.Trade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Trade")
@Table(name = "trade")
@SQLDelete(sql =
        "UPDATE trade " +
                "SET deletedAt = CURRENT_TIMESTAMP " +
                "WHERE id = ?")
@Loader(namedQuery = "findTagById")
@NamedQuery(name = "findTagById", query =
        "SELECT t " +
                "FROM Trade t " +
                "WHERE t.id = ?1 " +
                "AND t.deletedAt IS NULL")
@Where(clause = "deletedAt IS NULL")
public class TradeDto extends AuditDto {
    @Id
    @GeneratedValue
    private UUID id;
    private String extId;
    private String instrument;
    private Double price;
    private Date openTime;
    private Trade.TradeState state;
    private Double initialUnits;
    private Double initialMarginRequired;
    private Double currentUnits;
    private Double realizedPL;
    private Double unrealizedPL;
    private Double averageClosePrice;
    private List<String> closingTransactionIDs;
    private Double financing;
    private Date closeTime;
    private ClientExtensions clientExtensions;
    @ManyToMany
    @JoinTable(name = "trade_comment",
            joinColumns = @JoinColumn(name = "trade_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentDto> comments = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "trade_tag",
            joinColumns = @JoinColumn(name = "trade_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagDto> tags = new ArrayList<>();
}
