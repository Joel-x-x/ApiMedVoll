package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultaCancelada implements ValidacionCancelarConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosCancelacionConsulta datos) {
        Boolean consultaCancelada = consultaRepository.existsByIdAndActivoFalse(datos.id());

        if (consultaCancelada)
            throw new ValidationException("Esta cita ya fue cancelada");

    }
}
