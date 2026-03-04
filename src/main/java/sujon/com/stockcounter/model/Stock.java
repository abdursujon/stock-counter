package sujon.com.stockcounter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
    public int itemsId;
    public String itemName;
    public int itemsCountPerId;
}
