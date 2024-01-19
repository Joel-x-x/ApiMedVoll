package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.validaciones.ValidacionConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errors.ValidacionIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendarConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidacionConsultas> validadores; // Inversión de dependencias DIP de SOLID

    public Consulta agendar(DatosAgendarConsulta datosAgendarConsulta) {


        if(!pacienteRepository.findById(datosAgendarConsulta.pacienteId()).isPresent()) {
            throw new ValidacionIntegridad("El paciente no existe");
        }

        if(datosAgendarConsulta.medicoId() != null && !medicoRepository.existsById(datosAgendarConsulta.medicoId())) {
            throw new ValidacionIntegridad("El medico no existe");
        }

        // Aplicando validaciones usando principios solid
        validadores.forEach(validacion -> validacion.validar(datosAgendarConsulta));

        if(seleccionarMedico(datosAgendarConsulta) == null) {
            throw new ValidationException("No existen medicos disponibles");
        }

        Medico medico = seleccionarMedico(datosAgendarConsulta);

        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.pacienteId()).get();

        Consulta consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha(), null, true);

        return consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.medicoId() != null) {
            return medicoRepository.getReferenceById(datosAgendarConsulta.medicoId());
        }

        if(datosAgendarConsulta.especialidad() == null) {
            throw new ValidacionIntegridad("No ha seleccionado ninguna especialidad para el médico");
        }

        return medicoRepository.seleccionarMedicoEspecialidadFecha(datosAgendarConsulta.fecha(), datosAgendarConsulta.especialidad());
    }
}

