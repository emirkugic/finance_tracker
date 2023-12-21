package ba.edu.ibu.finance_tracker.rest.controllers;

import ba.edu.ibu.finance_tracker.core.model.Alert;
import ba.edu.ibu.finance_tracker.core.service.AlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class AlertControllerTest {

    @Mock
    private AlertService alertService;

    @InjectMocks
    private AlertController alertController;

    private MockMvc mockMvc;

    @Test
    void createAlert() throws Exception {
        Alert alert = new Alert();
        alert.setId("1");
        alert.setUserId("userId");
        alert.setMessage("Test Message");
        alert.setAlertDate(new Date());

        when(alertService.createAlert(any())).thenReturn(alert);

        mockMvc = standaloneSetup(alertController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"userId\",\"message\":\"Test Message\",\"alertDate\":\"2023-11-25T12:00:00\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("userId"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Test Message"));
    }

    @Test
    void getAllAlerts() throws Exception {
        Alert alert1 = new Alert();
        alert1.setId("1");
        alert1.setUserId("userId");
        alert1.setMessage("Test Message 1");
        alert1.setAlertDate(new Date());

        Alert alert2 = new Alert();
        alert2.setId("2");
        alert2.setUserId("userId");
        alert2.setMessage("Test Message 2");
        alert2.setAlertDate(new Date());

        List<Alert> alerts = Arrays.asList(alert1, alert2);

        when(alertService.getAllAlerts()).thenReturn(alerts);

        mockMvc = standaloneSetup(alertController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/alerts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"));
    }

}
