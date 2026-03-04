package sujon.com.stockcounter.service;

import sujon.com.stockcounter.repository.entity.OrderEntity;

import java.util.Date;
import java.util.List;

public interface StockReport {
    int totalItemInStock();
    int itemsOnStockPerItem(int itemId);
    int totalOrderAmountByACustomer(int customerId);
    int totalItemsPurchaseOnDate(Date date);
    List<OrderEntity> getListOfAllOrdersByACustomer(int customerId);
    List<OrderEntity> getListOfAllOrdersByAllCustomers();
}
