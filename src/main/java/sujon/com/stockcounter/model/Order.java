package sujon.com.stockcounter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class Order {
    public Customer customer;
    public int orderId;
    public int amount;
    public Date date;
}
