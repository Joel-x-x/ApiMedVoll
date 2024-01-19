package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidacionConsultas {
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosAgendarConsulta datos) {
        if(datos.medicoId() == null) {
            return;
        }

        var medicoActivo = medicoRepository.existsByIdAndActivoTrue(datos.medicoId());

        if(!medicoActivo) {
            throw new ValidationException("No se puede agendar citas con medicos inactivos");
        }
    }
}
