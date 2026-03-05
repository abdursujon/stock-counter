package sujon.com.stockcounter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTotalItems() throws Exception{
        mockMvc.perform(get("/api/stock/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
    }

    @Test
    void shouldReturnItemsPerStock() throws Exception{
        mockMvc.perform(get("/api/stock/item-per-stock/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("8000"));
    }

    @Test
    void shouldReturnStatus404ForItemNotFound() throws Exception{
        mockMvc.perform(get("/api/stock/item-per-stock/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void  shouldReturnUpdateStockCountForAnItem() throws Exception{
        mockMvc.perform(put("/api/stock/item-per-stock/2?newCount=8000"))
                .andExpect(status().isOk())
                .andExpect(content().string("8000"));
    }

    @Test
    void  shouldReturnStatus404ForCountForAnItemNotFound() throws Exception{
        mockMvc.perform(put("/api/stock/item-per-stock/9999?newCount=8000"))
                .andExpect(status().isNotFound());
    }

    // Test to check @PostMapping("/customer") works
    @Test
    void  shouldReturnStatus201ForAddCustomer() throws Exception{
        mockMvc.perform(post("/api/stock/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 88, \"name\": \"Michael\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteCustomerByTheirId() throws Exception{
        mockMvc.perform(get("/api/stock/get-customer/1")).andExpect(status().isOk());
        mockMvc.perform(delete("/api/stock/delete-customer/1"))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/stock/customer/1"))
                .andExpect(status().isNotFound());
    }
}
