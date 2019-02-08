package pl.jdev.opes.db.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import pl.jdev.opes.db.dto.metadata.Taggable;
import pl.jdev.opes_commons.domain.broker.BrokerName;

import javax.persistence.*;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//@NamedQueries({
//        @NamedQuery(name = "findAllAccounts",
//                query = "SELECT a " +
//                        "FROM Account a " +
//                        "WHERE a.deletedAt IS NULL"),
//        @NamedQuery(name = "findAccountById",
//                query = "SELECT a " +
//                        "FROM Account a " +
//                        "WHERE a.id = :id " +
//                        "AND a.deletedAt IS NULL"),
//TODO: how to join these detials?
//        @NamedQuery(name = "findAccountByExtId",
////                hints = {@QueryHint(name = QueryHints.LOADGRAPH,
////                        value = "graph.Account.accountDetails")},
//                query = "SELECT a " +
//                        "FROM Account a " +
//                        "JOIN a.accountDetails ad " +
//                        "WHERE ad.parent = a " +
//                        "AND ad.extId = :extId " +
//                        "AND a.deletedAt IS NULL"),
//        @NamedQuery(name = "findAccountByExtId",
//                query = "SELECT a " +
//                        "FROM Account a " +
//                        "WHERE a.accountDetails.extId = :extId " +
//                        "AND a.deletedAt IS NULL")
//})

//@Where(clause = "deletedAt IS NULL")
//@NamedEntityGraph(name = "graph.Account.accountDetails",
//        attributeNodes = @NamedAttributeNode(value = "accountDetails"))
@Getter
@Setter
@Entity(name = "Account")
@Table(name = "accounts")
//@Loader(namedQuery = "findAccountById")
@SQLDelete(sql = "UPDATE  account " +
        "SET deletedAt = CURRENT_TIMESTAMP " +
        "WHERE id = ?")
public class AccountDto extends DeletableAuditDto implements Taggable {
    private static final long serialVersionUID = -6238218905107699748L;
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String extId;
    @Column
    @Enumerated(EnumType.STRING)
    private BrokerName brokerName;
    @Column(length = 3)
    private Currency currency;
    @Column
    private Double balance;
    @Column
    private boolean isSynced;
    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<OrderDto> orders = new HashSet<>();
    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<TradeDto> trades = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "account_tag",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagDto> tags = new HashSet<>();
}