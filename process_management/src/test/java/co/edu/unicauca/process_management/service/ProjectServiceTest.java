package co.edu.unicauca.process_management.service;

import co.edu.unicauca.process_management.entity.ModalityEnum;
import co.edu.unicauca.process_management.entity.ProjectContext;
import co.edu.unicauca.process_management.entity.ProjectModel;
import co.edu.unicauca.process_management.entity.StatusEnum;
import co.edu.unicauca.process_management.exception.CorreosIgualesException;
import co.edu.unicauca.process_management.exception.MaximoIntentosException;
import co.edu.unicauca.process_management.exception.UsuarioNoRegistradoException;
import co.edu.unicauca.process_management.repository.ProjectRepository;
import co.edu.unicauca.process_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    private ProjectModel project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new ProjectModel();
        project.setAtrDirectorEmail("director@unicauca.edu.co");
        project.setAtrStudent1Email("student1@unicauca.edu.co");
        project.setAtrStudent2Email("student2@unicauca.edu.co");
        project.setAtrStatus(StatusEnum.INICIO);
    }

    // ---------- crearProyecto() ----------

    @Test
    void crearProyecto_DeberiaLanzarExcepcion_SiDirectorYEstudianteSonIguales() {
        project.setAtrStudent1Email("director@unicauca.edu.co");

        assertThrows(CorreosIgualesException.class, () ->
                projectService.crearProyecto(project)
        );

        verify(projectRepository, never()).save(any());
    }

    @Test
    void crearProyecto_DeberiaLanzarExcepcion_SiDirectorYCodirectorSonIguales() {
        project.setAtrCodirectorEmail("director@unicauca.edu.co");

        assertThrows(CorreosIgualesException.class, () ->
                projectService.crearProyecto(project)
        );
    }

    @Test
    void crearProyecto_DeberiaLanzarExcepcion_SiEstudiantesTienenElMismoCorreo() {
        project.setAtrStudent2Email("student1@unicauca.edu.co");

        assertThrows(CorreosIgualesException.class, () ->
                projectService.crearProyecto(project)
        );
    }

    @Test
    void crearProyecto_DeberiaGuardarProyecto_SiUsuariosExisten() {
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(projectRepository.save(any(ProjectModel.class))).thenReturn(project);

        ProjectModel result = projectService.crearProyecto(project);

        assertNotNull(result);
        verify(projectRepository, times(1)).save(any(ProjectModel.class));
        verify(userRepository, atLeast(2)).existsById(anyString());
        assertEquals(0, result.getAtrNumberOfAttempts());
    }

    @Test
    void crearProyecto_DeberiaLanzarExcepcion_SiUsuarioNoRegistrado() {
        when(userRepository.existsById("director@unicauca.edu.co")).thenReturn(false);

        assertThrows(UsuarioNoRegistradoException.class, () ->
                projectService.crearProyecto(project)
        );
    }

    // ---------- avanzarEstado() ----------

    @Test
    void avanzarEstado_DeberiaGuardarProyectoActualizado() {
        ProjectModel existing = new ProjectModel();
        existing.setAtrStatus(StatusEnum.INICIO);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(projectRepository.save(any(ProjectModel.class))).thenReturn(existing);

        ProjectModel result = projectService.avanzarEstado(1L);

        assertNotNull(result);
        verify(projectRepository, times(1)).save(any(ProjectModel.class));
    }

    // ---------- retrocederEstado() ----------

    @Test
    void retrocederEstado_DeberiaGuardarProyectoActualizado() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(ProjectModel.class))).thenReturn(project);

        ProjectModel result = projectService.retrocederEstado(1L);

        assertNotNull(result);
        verify(projectRepository, times(1)).save(any(ProjectModel.class));
    }

    // ---------- corregirProyecto() ----------

    @Test
    void corregirProyecto_DeberiaGuardarProyectoActualizado() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(ProjectModel.class))).thenReturn(project);

        ProjectModel result = projectService.corregirProyecto(1L);

        assertNotNull(result);
        verify(projectRepository, atLeastOnce()).save(any(ProjectModel.class));
    }



    // ---------- actualizarProyecto() ----------

    @Test
    void actualizarProyecto_DeberiaActualizarCamposCorrectamente() {
        ProjectModel existing = new ProjectModel();
        existing.setAtrTitle("Old");
        when(projectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(projectRepository.save(any(ProjectModel.class))).thenReturn(existing);

        ProjectModel updated = new ProjectModel();
        updated.setAtrTitle("New");
        updated.setAtrDirectorEmail("new@unicauca.edu.co");

        ProjectModel result = projectService.actualizarProyecto(1L, updated);

        assertEquals("New", result.getAtrTitle());
        assertEquals("new@unicauca.edu.co", result.getAtrDirectorEmail());
        verify(projectRepository).save(any(ProjectModel.class));
    }

    @Test
    void actualizarProyecto_DeberiaLanzarExcepcion_SiNoExisteProyecto() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                projectService.actualizarProyecto(1L, new ProjectModel())
        );
    }

    // ---------- obtenerProyecto() ----------

    @Test
    void obtenerProyecto_DeberiaRetornarProyectoExistente() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectModel result = projectService.obtenerProyecto(1L);

        assertNotNull(result);
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerProyecto_DeberiaLanzarExcepcion_SiNoExiste() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                projectService.obtenerProyecto(1L)
        );
    }

    // ---------- listarProyectos() y filtros ----------

    @Test
    void listarProyectos_DeberiaRetornarLista() {
        when(projectRepository.findAll()).thenReturn(List.of(project));

        List<ProjectModel> result = projectService.listarProyectos();

        assertEquals(1, result.size());
    }

    @Test
    void listarPorModality_DeberiaRetornarLista() {
        when(projectRepository.findByAtrModality(ModalityEnum.PROFESSIONAL))
                .thenReturn(List.of(project));

        List<ProjectModel> result = projectService.listarPorModality(ModalityEnum.PROFESSIONAL);

        assertEquals(1, result.size());
    }

    @Test
    void obtenerProyectosPorDirector_DeberiaRetornarLista() {
        when(projectRepository.findByAtrDirectorEmail("dir@unicauca.edu.co"))
                .thenReturn(List.of(project));

        List<ProjectModel> result = projectService.obtenerProyectosPorDirector("dir@unicauca.edu.co");

        assertEquals(1, result.size());
    }

    @Test
    void obtenerProyectosPorEstadoYCorreo_DeberiaRetornarLista() {
        when(projectRepository.findByAtrStatusAndAtrDirectorEmail(StatusEnum.INICIO, "dir@unicauca.edu.co"))
                .thenReturn(List.of(project));

        List<ProjectModel> result = projectService.obtenerProyectosPorEstadoYCorreo(StatusEnum.INICIO, "dir@unicauca.edu.co");

        assertEquals(1, result.size());
    }
}
