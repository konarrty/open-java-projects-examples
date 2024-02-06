package com.example.testbyport;

import com.example.testbyport.domain.Range;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/sql/clear-all-test-data.sql", "/sql/add-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class TaskControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void receivingAllTasksByEmployeeIdAndValidMonth() throws Exception {

        String employeeId = "2";
        String monthNumber = "3";

        mockMvc.perform(get("/api/tasks/plans")
                        .param("employeeId", employeeId)
                        .param("monthNumber", monthNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].name").value("test8"))
                .andExpect(jsonPath("$[2].range").value(new Range(1, 7)));

    }

}
