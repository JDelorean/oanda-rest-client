package pl.jdev.opes.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import pl.jdev.opes.db.dto.metadata.Taggable;
import pl.jdev.opes_commons.db.DeletableAuditDto;
import pl.jdev.opes_commons.domain.broker.BrokerName;

import javax.persistence.*;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Account")
@Table(name = "accounts")
@SQLDelete(sql = "UPDATE  account " +
        "SET deletedAt = CURRENT_TIMESTAMP " +
        "WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends DeletableAuditDto implements Taggable {
    private static final long serialVersionUID = -6238218905107699748L;
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;
    @Column(unique = true, nullable = false, updatable = false)
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