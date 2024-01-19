package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioAtencionClinica implements ValidacionConsultas {

    public void validar(DatosAgendarConsulta datos) {
        Boolean domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

        Boolean fueraHorario = (datos.fecha().getHour() > 19 || datos.fecha().getHour() < 7);

        if(domingo || fueraHorario) {
            throw new ValidationException("Horario de atención del cliente es de lunes a sábado de 7:00 a 19:00 horas");
        }
    }
}
