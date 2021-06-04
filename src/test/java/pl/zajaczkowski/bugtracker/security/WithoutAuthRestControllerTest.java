package pl.zajaczkowski.bugtracker.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.zajaczkowski.bugtracker.auth.CustomUserDetailsService;
import pl.zajaczkowski.bugtracker.project.Project;
import pl.zajaczkowski.bugtracker.project.ProjectService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WithoutAuthRestController.class)
@ExtendWith(MockitoExtension.class)
class WithoutAuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjectService service;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void status_should_be_ok() throws Exception {

        //given
        var project = new Project();
        project.setName("project6");
        var optionalProject = Optional.of(project);

        //when
        when(service.findProjectById(any(Long.class))).thenReturn(optionalProject);

        //then
        this.mockMvc.perform(get("/api"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("project6"));

    }
}