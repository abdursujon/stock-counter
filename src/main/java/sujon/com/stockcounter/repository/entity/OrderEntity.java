package sujon.com.stockcounter.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    private int orderId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    private int amount;
    private Date date;
}
