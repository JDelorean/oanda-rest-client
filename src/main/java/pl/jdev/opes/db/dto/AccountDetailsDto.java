package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import pl.jdev.opes_commons.domain.account.GuaranteedStopLossOrderMode;

import javax.persistence.*;
import java.util.Currency;
import java.util.Date;

@Getter
@Setter
@Entity(name = "AccountDetails")
@Table(name = "account_details")

@SQLDelete(sql = "UPDATE account_details " +
        "SET deleted = CURRENT_TIMESTAMP " +
        "WHERE accDetId = ?")
//@Loader(namedQuery = "findAccountDetailsById")
//@NamedQuery(name = "findAccountDetailsById",
//        query = "SELECT ad " +
//                "FROM AccountDetails ad " +
//                "WHERE ad.accDetId = ?1 " +
//                "AND ad.deletedAt IS NULL")
//@Where(clause = "deletedAt IS NULL")
public class AccountDetailsDto extends DeletableAuditDto {
    private static final long serialVersionUID = -5914626362103310676L;
    @Id
    @GeneratedValue
    private Long id;
//    @OneToOne(fetch = FetchType.EAGER,
//            optional = false)
//    @JoinColumn(name = "account_id",
//            nullable = false)
//    private AccountDto parent;
    @Column(unique = true)
    private String extId;
    @Column
    private String alias;
    @Column(length = 3)
    private Currency currency;
    @Column
    private Double balance;
    @Column
    private String createdByUserID;
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
}
