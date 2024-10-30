package com.npichuzhkin.ObjectMapperTask;

import com.npichuzhkin.ObjectMapperTask.controllers.ProductsController;
import com.npichuzhkin.ObjectMapperTask.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(new ProductsController(productService)).build();
    }

    @Test
    public void showAllProductsShouldReturnProductsJson() throws Exception {
        String allProductsJson = "[\n" +
                "        {\n" +
                "        \"productId\": \"b76f3330-ea2a-4a2f-8b4d-84a7d20af94c\",\n" +
                "        \"name\": \"Laptop\",\n" +
                "        \"description\": \"15-inch laptop with 16GB RAM and 512GB SSD\",\n" +
                "        \"price\": 1200.00,\n" +
                "        \"quantityInStock\": 10,\n" +
                "        \"order\": null\n" +
                "        },\n" +
                "        {\n" +
                "        \"productId\": \"c16c3400-1073-4dbe-91de-1ff178769078\",\n" +
                "        \"name\": \"Smartphone\",\n" +
                "        \"description\": \"5G smartphone with 128GB storage\",\n" +
                "        \"price\": 699.99,\n" +
                "        \"quantityInStock\": 25,\n" +
                "        \"order\": null\n" +
                "        }\n" +
                "        ]";
        Mockito.when(productService.findAll()).thenReturn(allProductsJson);

        mockMvc.perform(get("/api/v1/products/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(allProductsJson));
    }

    @Test
    public void showOneProductShouldReturnProductJson() throws Exception {
        UUID productId = UUID.randomUUID();
        String productJson = "{\n" +
                "\t\t\"productId\": \"b76f3330-ea2a-4a2f-8b4d-84a7d20af94c\",\n" +
                "\t\t\"name\": \"Laptop\",\n" +
                "\t\t\"description\": \"15-inch laptop with 16GB RAM and 512GB SSD\",\n" +
                "\t\t\"price\": 1200.00,\n" +
                "\t\t\"quantityInStock\": 10,\n" +
                "\t\t\"order\": null\n" +
                "\t}";

        Mockito.when(productService.findById(productId)).thenReturn(productJson);

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(productJson));
    }

    @Test
    public void addNewProductShouldReturnOkStatus() throws Exception {
        String newProductJson = "{\n" +
                "\t\"name\": \"New Product\",\n" +
                "\t\"description\": \"New Product\",\n" +
                "\t\"price\": 699.99,\n" +
                "\t\"quantityInStock\": 25\n" +
                "}";

        mockMvc.perform(post("/api/v1/products/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isOk());

        Mockito.verify(productService).save(eq(newProductJson));
    }

    @Test
    public void updateProductShouldReturnOkStatus() throws Exception {
        UUID productId = UUID.randomUUID();
        String updatedProductJson = "{\n" +
                "\t\"name\": \"Updated\",\n" +
                "\t\"description\": \"Updated\",\n" +
                "\t\"price\": 699.99,\n" +
                "\t\"quantityInStock\": 25\n" +
                "}";

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk());

        Mockito.verify(productService).update(eq(productId), eq(updatedProductJson));
    }

    @Test
    public void deleteProductShouldReturnOkStatus() throws Exception {
        UUID productId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(productService).delete(eq(productId));
    }
}