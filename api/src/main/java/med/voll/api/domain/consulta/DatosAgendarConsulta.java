package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(
        Long id,
        @NotNull
        Long pacienteId,
        Long medicoId,
        @Future
        @NotNull
        LocalDateTime fecha,
        Especialidad especialidad
) {
}
