package sujon.com.stockcounter.service;

import org.springframework.stereotype.Service;
import sujon.com.stockcounter.repository.OrderRepository;
import sujon.com.stockcounter.repository.StockRepository;
import sujon.com.stockcounter.repository.entity.OrderEntity;
import sujon.com.stockcounter.repository.entity.StockEntity;

import java.util.*;

@Service
public class DailyStockReport implements StockReport {
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;

    public DailyStockReport(StockRepository stockRepository, OrderRepository orderRepository){
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public int totalItemInStock() {
        return (int) stockRepository.count();
    }

    @Override
    public int itemsOnStockPerItem(int itemId) {
        return stockRepository.findById(itemId)
                .map(StockEntity::getItemsCountPerId)
                .orElse(0);
    }

    @Override
    public int totalOrderAmountByACustomer(int customerId) {
       return orderRepository.findAll().stream()
               .filter(o -> o.getCustomer()
               .getId() == customerId)
               .mapToInt(OrderEntity::getAmount).sum();
    }

    @Override
    public int totalItemsPurchaseOnDate(Date date) {
        return orderRepository.findAll().stream()
                .filter(o -> date.equals(o.getDate()))
                .mapToInt(OrderEntity::getAmount).sum();
    }

    @Override
    public List<OrderEntity> getListOfAllOrdersByACustomer(int customerId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getCustomer().getId() == customerId)
                .toList();
    }

    @Override
    public List<OrderEntity> getListOfAllOrdersByAllCustomers(){
        return orderRepository.findAll();
    }


    // Update stock count for an item which PutMapping controller will implement as an endpoint
    public StockEntity updateStockCountForAnItem(int itemId, int newCount){
        StockEntity stock = stockRepository.findById(itemId).orElse(null);
        if(stock == null) {
            return null;
        }
        stock.setItemsCountPerId(newCount);
        return stockRepository.save(stock);
    }

}
