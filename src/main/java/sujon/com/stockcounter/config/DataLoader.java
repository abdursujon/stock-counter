package sujon.com.stockcounter.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sujon.com.stockcounter.model.Customer;
import sujon.com.stockcounter.model.Order;
import sujon.com.stockcounter.model.Stock;
import sujon.com.stockcounter.repository.CustomerRepository;
import sujon.com.stockcounter.repository.OrderRepository;
import sujon.com.stockcounter.repository.StockRepository;
import sujon.com.stockcounter.repository.entity.CustomerEntity;
import sujon.com.stockcounter.repository.entity.OrderEntity;
import sujon.com.stockcounter.repository.entity.StockEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

@Component
public class DataLoader implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public DataLoader(CustomerRepository customerRepository,
                      OrderRepository orderRepository,
                      StockRepository stockRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCustomers();
        loadStocks();
        loadOrders();
    }

    private void loadCustomers() {
        InputStream is = Customer.class.getClassLoader().getResourceAsStream("customer/customer.csv");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                CustomerEntity customer = new CustomerEntity();
                customer.setId(Integer.parseInt(parts[0].trim()));
                customer.setName(parts[1].trim());
                customerRepository.save(customer);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load customers from csv", e);
        }
    }

    private void loadOrders() {
        InputStream is = Order.class.getClassLoader().getResourceAsStream("customer/order.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            br.readLine();
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int customerId = Integer.parseInt(parts[0].trim());
                CustomerEntity customer = customerRepository.findById((Integer) customerId).orElse(null);

                OrderEntity order = new OrderEntity();
                order.setCustomer(customer);
                order.setOrderId(Integer.parseInt(parts[1].trim()));
                order.setAmount(Integer.parseInt(parts[2].trim()));
                order.setDate(sdf.parse(parts[3].trim()));
                orderRepository.save(order);
            }
        } catch (Exception e) {
            throw new RuntimeException("Order csv not found. Or it's not formated correctly.");
        }
    }

    private void loadStocks() {
        InputStream is = Stock.class.getClassLoader().getResourceAsStream("stock/items.csv");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                StockEntity stock = new StockEntity();
                String[] parts = line.split(",", 3);
                stock.setItemId(Integer.parseInt(parts[0].trim()));
                stock.setItemName(parts[1].trim());
                stock.setItemsCountPerId(Integer.parseInt(parts[2].trim()));
                stockRepository.save(stock);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load stock from csv file.");
        }
    }
}
