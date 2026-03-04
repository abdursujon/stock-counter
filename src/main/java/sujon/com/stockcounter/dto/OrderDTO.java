package sujon.com.stockcounter.dto;

import java.util.Date;

public record OrderDTO(
        String customerName,
        int amount,
        Date date
) {
}