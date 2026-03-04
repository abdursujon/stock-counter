package sujon.com.stockcounter.model;

import java.util.List;

public record StockStatistics(
    int totalItems,
    int itemsOnStockPerItem,
    int totalOrderAmountByACustomer,
    int totalItemsPurchaseOnDate,
    List<Order> getListOfAllOrdersByACustomer,
    List<Order> getListOfAllOrdersByAllCustomers
    ) {
}
