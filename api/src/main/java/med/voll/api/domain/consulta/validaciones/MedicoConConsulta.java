package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidacionConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(DatosAgendarConsulta datos) {
        var consultaInicio = datos.fecha();
        var consultaFin = datos.fecha().plusHours(1);

        var medicoEnConsulta = consultaRepository.existsByMedicoIdAndFechaBetween(datos.medicoId(), consultaInicio, consultaFin);

        if(medicoEnConsulta) {
            throw new ValidationException("Este médico se encuentra en una consulta");
        }
    }
}
