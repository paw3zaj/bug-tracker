package pl.zajaczkowski.bugtracker.project;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import pl.zajaczkowski.bugtracker.auth.CustomUserDetailsService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectRESTController.class)
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProjectRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private ProjectService service;
    @MockBean
    private ProjectModelAssembler assembler;

    @Test
    void should_return_name() throws Exception {

        var project = new Project();
        project.setName("project6");
        var optionalProject = Optional.of(project);

        when(service.findProjectById(any(Long.class))).thenReturn(optionalProject);
        when(assembler.toModel(any(Project.class))).thenReturn(EntityModel.of(project,
                linkTo(methodOn(ProjectRESTController.class).showProject(project.getId())).withSelfRel()));


        this.mockMvc.perform(get("/api/projects/68"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("project6"));
    }

}