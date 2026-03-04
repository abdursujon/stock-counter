package sujon.com.stockcounter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujon.com.stockcounter.repository.entity.OrderEntity;
import sujon.com.stockcounter.repository.entity.StockEntity;
import sujon.com.stockcounter.service.DailyStockReport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final DailyStockReport stockReport;

    public StockController(DailyStockReport stockReport) {
        this.stockReport = stockReport;
    }

    @GetMapping("/total")
    public int getTotalItems() {
        return stockReport.totalItemInStock();
    }

    // Example call:  http://localhost:8080/api/stock/item-per-stock/2
    @GetMapping("/item-per-stock/{itemId}")
    public ResponseEntity<Integer> getItemsPerStock(@PathVariable int itemId) throws Exception {
        int count = stockReport.itemsOnStockPerItem(itemId);
        if(count == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(count);
    }

    // Example call:  http://localhost:8080/api/stock/total-order-amount/4
    @GetMapping("/total-order-amount/{customerId}")
    public int getTotalOrderAmountByACustomer(@PathVariable int customerId) throws Exception{
        return stockReport.totalOrderAmountByACustomer(customerId);
    }

    // Example call:  http://localhost:8080/api/stock/total-purchase-on-date?date=02-03-2026
    @GetMapping("/total-purchase-on-date")
    public int getTotalItemsPurchaseOnDate(@RequestParam String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return stockReport.totalItemsPurchaseOnDate(sdf.parse(date));
    }

    // Example call:  http://localhost:8080/api/stock/order/3
    @GetMapping("/order/{customerId}")
    public List<OrderEntity> getListOfAllOrdersByACustomer(@PathVariable int customerId) throws Exception{
        return stockReport.getListOfAllOrdersByACustomer(customerId);
    }

    // Example call:  http://localhost:8080/api/stock/order/3
    @GetMapping("/all-orders")
    public List<OrderEntity> getListOfAllOrdersByAllCustomers() throws Exception{
        return stockReport. getListOfAllOrdersByAllCustomers();
    }

    // Update the stock count for an item
    @PutMapping("/item-per-stock/{itemId}")
    public ResponseEntity<Integer> updateStockCountForAnItem(@PathVariable int itemId, @RequestParam int newCount){
        StockEntity updated = stockReport.updateStockCountForAnItem(itemId, newCount);
        if(updated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated.getItemsCountPerId());
    }
}
