package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteConConsulta implements ValidacionConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(DatosAgendarConsulta datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);

        var PacienteConConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(datos.pacienteId(), primerHorario, ultimoHorario);

        if(PacienteConConsulta) {
            throw new ValidationException("Un paciente solo puede tener una consulta al día");
        }


    }
}
