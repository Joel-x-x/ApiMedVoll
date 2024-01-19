package med.voll.api.domain.consulta;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.validaciones.ValidacionCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelarConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidacionCancelarConsulta> validaciones;

    @Transactional
    public Consulta cancelarConsulta(DatosCancelacionConsulta datosCancelacionConsulta) {
        if(datosCancelacionConsulta.id() == null || !consultaRepository.existsById(datosCancelacionConsulta.id())) {
            throw new ValidationException("La consulta no existe");
        }

        validaciones.forEach(x -> x.validar(datosCancelacionConsulta));

        Consulta consulta = consultaRepository.getReferenceById(datosCancelacionConsulta.id());

        consulta.setMotivoCancelacion(datosCancelacionConsulta.motivoCancelacion());
        consulta.desactivar();

        return consultaRepository.save(consulta);
    }
}
