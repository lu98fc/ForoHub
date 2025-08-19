package foro.hub.api.domain.respuesta;

import foro.hub.api.domain.respuesta.Respuesta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Optional<Respuesta> findByIdAndActivoTrue(Long id);
    Optional<Respuesta> findByMensajeAndTopicoIdAndActivoTrue(@NotBlank String mensaje, @NotNull Long aLong);
    Page<Respuesta> findByActivoTrue(Pageable paginacion);
}
