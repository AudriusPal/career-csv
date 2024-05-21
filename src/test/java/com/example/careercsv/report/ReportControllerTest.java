package com.example.careercsv.report;

import com.example.careercsv.report.service.ReportService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = {"test"})
@WebMvcTest(controllers = {ReportController.class})
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @DisplayName("Test report from file")
    @Test
    void testGenerateReport() throws Exception {
        ReportDTO reportDTO = new ReportDTO(1.0, 2, "1", LocalDate.of(2010, 5, 2));
        when(reportService.generateReport(any(MultipartFile.class))).thenReturn(reportDTO);
        MockMultipartFile file = new MockMultipartFile("file", "originalName.csv", "text/csv", "".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/report/generate")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRevenue", Matchers.is(1.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uniqueCustomersNumber", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mostPopularItemId", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateWithHighestRevenue", Matchers.is("2010-05-02")));
    }
}
