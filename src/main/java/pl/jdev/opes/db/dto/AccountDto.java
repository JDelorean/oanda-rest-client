package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.jdev.opes_commons.domain.account.GuaranteedStopLossOrderMode;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity(name = "Account")
@Table(name = "account")
@SQLDelete(sql =
        "UPDATE account " +
                "SET deletedAt = CURRENT_TIMESTAMP " +
                "WHERE id = ?")
@Loader(namedQuery = "findAccountById")
@NamedQuery(name = "findAccountById", query =
        "SELECT a " +
                "FROM Account a " +
                "WHERE a.id = ?1 " +
                "AND a.deletedAt IS NULL")
@Where(clause = "deletedAt IS NULL")
public class AccountDto extends AuditDto {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String extId;
    @Column
    private Currency currency;
    @Column
    private Double balance;
    @Column
    private Date extCreatedTime;
    @Column
    private GuaranteedStopLossOrderMode guaranteedStopLossOrderMode;
    @Column
    private Double pl;
    @Column
    private Double resettablePL;
    @Column
    private Date resettablePLTime;
    @Column
    private Double financing;
    @Column
    private Double commission;
    @Column
    private Double guaranteedExecutionFees;
    @Column
    private Double marginRate;
    @Column
    private Date marginCallEnterTime;
    @Column
    private Integer marginCallExtensionCount;
    @Column
    private Date lastMarginCallExtensionTime;
    @Column
    private Integer openTradeCount;
    @Column
    private Integer openPositionCount;
    @Column
    private Integer pendingOrderCount;
    @Column
    private Boolean hedgingEnabled;
    @Column
    private Double unrealizedPL;
    @Column
    private Double nav;
    @Column
    private Double marginUsed;
    @Column
    private Double marginAvailable;
    @Column
    private Double positionValue;
    @Column
    private Double marginCloseoutUnrealizedPL;
    @Column
    private Double marginCloseoutNAV;
    @Column
    private Double marginCloseoutMarginUsed;
    @Column
    private Double marginCloseoutPercent;
    @Column
    private Double marginCloseoutPositionValue;
    @Column
    private Double withdrawalLimit;
    @Column
    private Double marginCallMarginUsed;
    @Column
    private Double marginCallPercent;
    @ManyToMany
    @JoinTable(name = "account_tag",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagDto> tags = new ArrayList<>();
}
