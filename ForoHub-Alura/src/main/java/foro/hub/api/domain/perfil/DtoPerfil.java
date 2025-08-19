package foro.hub.api.domain.perfil;

import jakarta.validation.constraints.NotBlank;

public record DtoPerfil(
        Long id,

        @NotBlank
        String nombre
) {}
