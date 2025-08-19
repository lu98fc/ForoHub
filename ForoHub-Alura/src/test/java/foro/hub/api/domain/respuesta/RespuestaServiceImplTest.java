package foro.hub.api.domain.respuesta;

import foro.hub.api.domain.curso.Curso;
import foro.hub.api.domain.curso.DtoCurso;
import foro.hub.api.domain.perfil.DtoPerfil;
import foro.hub.api.domain.perfil.Perfil;
import foro.hub.api.domain.topico.DtoTopico;
import foro.hub.api.domain.topico.EstadosTopico;
import foro.hub.api.domain.topico.Topico;
import foro.hub.api.domain.topico.TopicoRepository;
import foro.hub.api.domain.usuario.DtoUsuario;
import foro.hub.api.domain.usuario.Usuario;
import foro.hub.api.domain.usuario.UsuarioRepository;
import foro.hub.api.infra.errores.ValidacionDeIntegridad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RespuestaServiceImplTest {

    @Mock
    private RespuestaRepository respuestaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private TopicoRepository topicoRepository;

    @InjectMocks
    private RespuestaServiceImpl respuestaService;

    private Usuario autor;
    private Topico topico;
    private DtoRespuesta dtoRespuesta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Usuario usuario = new Usuario(new DtoUsuario(1L, "juan","juan@mi.com", "12345", 1L), new Perfil(new DtoPerfil(1L,"Estiduante")), null); // Simula un usuario
        DtoTopico dto = new DtoTopico(1L, "Programacion", "Me ayudan",1L, 1L, EstadosTopico.NO_RESPONDIDO);
        Curso curso = new Curso( new DtoCurso(1L, "java", "Desarrollo"));
        topico = new Topico(dto, usuario, curso);
        dtoRespuesta = new DtoRespuesta(null, "Este es el mensaje de la respuesta", topico.getId(), autor.getId(), LocalDateTime.now(), false);
    }

    @Test
    @DisplayName("Debería registrar una respuesta exitosamente cuando los datos son válidos")
    void registrarRespuesta_Exitoso() {
        // Given
        when(usuarioRepository.findByIdAndActivoTrue(autor.getId())).thenReturn(Optional.of(autor));
        when(topicoRepository.findByIdAndActivoTrue(topico.getId())).thenReturn(Optional.of(topico));

        Respuesta respuestaGuardada = new Respuesta(dtoRespuesta, autor, topico);
        when(respuestaRepository.save(any(Respuesta.class))).thenReturn(respuestaGuardada);

        // When
        DtoRespuestaDetalle resultado = respuestaService.registrarRespuesta(dtoRespuesta);

        // Then
        assertNotNull(resultado);
        assertEquals(dtoRespuesta.mensaje(), resultado.mensaje());
        assertEquals(autor.getNombre(), resultado.autorId());
        assertEquals(topico.getTitulo(), resultado.topicoId());
    }

    @Test
    @DisplayName("Debería lanzar ValidacionDeIntegridad si el tópico no existe")
    void registrarRespuesta_Falla_TopicoInexistente() {
        // Given
        when(usuarioRepository.findByIdAndActivoTrue(autor.getId())).thenReturn(Optional.of(autor));
        when(topicoRepository.findByIdAndActivoTrue(topico.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ValidacionDeIntegridad.class, () -> respuestaService.registrarRespuesta(dtoRespuesta));
    }
}