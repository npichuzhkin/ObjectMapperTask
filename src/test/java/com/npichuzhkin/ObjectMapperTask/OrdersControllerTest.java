package com.npichuzhkin.ObjectMapperTask;

import com.npichuzhkin.ObjectMapperTask.controllers.OrdersController;
import com.npichuzhkin.ObjectMapperTask.services.OrderService;
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


@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(new OrdersController(orderService)).build();
    }

    @Test
    public void showOneOrderShouldReturnOrderJson() throws Exception {
        UUID orderId = UUID.randomUUID();
        String orderJson = "{\n" +
                "\"orderId\":\""+ orderId+ "\",\n" +
                "\"costumer\":{\n" +
                "\"costumerID\":\"3eaca1ec-3c9b-41ba-b5ad-9963594ce181\",\n" +
                "\"firstName\":\"John\",\n" +
                "\"lastName\":\"Doe\",\n" +
                "\"email\":\"john.doe@example.com\",\n" +
                "\"contactNumber\":\"+1234567890\"\n" +
                "},\n" +
                "\"products\":[\n" +
                "],\n" +
                "\"orderDate\":1730232000000,\n" +
                "\"shippingAddress\":\"123MainSt,Springfield,IL\",\n" +
                "\"totalPrice\":0.00,\n" +
                "\"orderStatus\":\"SHIPPED\"\n" +
                "}";

        Mockito.when(orderService.findById(orderId)).thenReturn(orderJson);

        mockMvc.perform(get("/api/v1/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(orderJson));
    }

    @Test
    public void addNewOrderShouldReturnOkStatus() throws Exception {
        String orderJson = "{\n" +
                "\t\"costumerId\": \"" + UUID.randomUUID() + "\",\n" +
                "\t\"products\": [ \"" + UUID.randomUUID() + "\", \"" + UUID.randomUUID() + "\"\n" +
                "\t],\n" +
                "\t\"shippingAddress\": \"some\"\n" +
                "}";

        mockMvc.perform(post("/api/v1/orders/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk());

        Mockito.verify(orderService).save(eq(orderJson));
    }
}
