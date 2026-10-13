package edu.dosw.parcial.controller;

import edu.dosw.parcial.main.ParcialApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MenuController.class)
@ContextConfiguration(classes = ParcialApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debe retornar el menú con platos, extras y métodos de entrega")
    void shouldReturnMenuCatalog() throws Exception {
        mockMvc.perform(get("/api/v1/menu"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.plates").isArray())
            .andExpect(jsonPath("$.extras").isArray())
            .andExpect(jsonPath("$.deliveryTypes").isArray())
            .andExpect(jsonPath("$.maxPlatesPerOrder").value(5))
            .andExpect(jsonPath("$.minOrderPrice").value(3500));
    }
}
