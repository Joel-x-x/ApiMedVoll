package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidacionConsultas {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DatosAgendarConsulta datos) {
        if(datos.pacienteId() == null) {
            return;
        }

        var pacienteActivo = pacienteRepository.existsByIdAndActivoTrue(datos.pacienteId());

        if(!pacienteActivo) {
            throw new ValidationException("No se puede agendar citas con pacientes inactivos");
        }

    }
}
