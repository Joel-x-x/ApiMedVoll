package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record DatosCancelacionConsulta(
        Long id,
        @NotBlank
        @JsonAlias("motivo_cancelacion")
        String motivoCancelacion
) {
        public DatosCancelacionConsulta(String motivoCancelacion, Long id) {
                this(id, motivoCancelacion);
        }
}
