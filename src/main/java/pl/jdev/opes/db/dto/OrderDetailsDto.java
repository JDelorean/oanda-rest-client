package pl.jdev.opes.db.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "OrderDetails")
@Table(name = "order_details")
public class OrderDetailsDto extends DeletableAuditDto {
    private static final long serialVersionUID = 220584671665393229L;
    @Id
    @GeneratedValue
    private Integer accDetId;
//    @OneToOne
//    @JoinColumn(name = "fk_order")
//    private OrderDto parent;
    @Column
    private String extId;
    @Column
    private Date extCreateTime;
}
