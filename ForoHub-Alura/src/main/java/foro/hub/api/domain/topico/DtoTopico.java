package foro.hub.api.domain.topico;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoTopico(
        Long id,

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotNull
        Long autorId,

        @NotNull
        Long cursoId,

        EstadosTopico estatus
) {}
