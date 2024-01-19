package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CancelarAntes24Horas implements ValidacionCancelarConsulta{

    @Autowired
    ConsultaRepository consultaRepository;
    @Override
    public void validar(DatosCancelacionConsulta datos) {
        Consulta consulta = consultaRepository.getReferenceById(datos.id());

        LocalDateTime fechaAhora = LocalDateTime.now();
        LocalDateTime fechaConsulta = consulta.getFecha();

        Boolean antes24Horas = Duration.between(fechaAhora, fechaConsulta).toHours() < 24;

        if(antes24Horas)
            throw new ValidationException("Solo se puede cancelar una cita con 24 horas de anticipación");
    }
}
