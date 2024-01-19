package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.paciente.Paciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;
    @Test
    @DisplayName("Debería retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoEspecialidadFecha() {
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 10);

        Medico medico = registrarMedico("Juan", "j@gmail.com","09430534","7765", Especialidad.CARDIOLOGIA, new Direccion("2343","2324",3,"ddd","ddd"));
        Paciente paciente = registarPaciente("Pepe","pepe@gmail.com","3243333","3453242",new Direccion("2343","2324",3,"ddd","ddd"));
        registrarConsulta(medico, paciente, proximoLunes10H);


        var medicoLibre = medicoRepository.seleccionarMedicoEspecialidadFecha(proximoLunes10H, Especialidad.CARDIOLOGIA);

        Assertions.assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Debería retornar un medico cuando el medico se encuentre disponible para esa fecha")
    void seleccionarMedicoEspecialidadFechaEscenario2() {
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 10);

        Medico medico = registrarMedico("Juan", "j@gmail.com","09430534","7765", Especialidad.CARDIOLOGIA, new Direccion("2343","2324",3,"ddd","ddd"));

        var medicoLibre = medicoRepository.seleccionarMedicoEspecialidadFecha(proximoLunes10H, Especialidad.CARDIOLOGIA);

        Assertions.assertThat(medicoLibre).isEqualTo(medico);
    }

    public void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha, null, true));
    }

    public Medico registrarMedico(String nombre, String email, String telefono, String documento, Especialidad especialidad, Direccion direccion) {
        Medico medico = em.persist(new Medico(null, nombre, email, true, telefono, documento, especialidad, direccion));
        return medico;
    }

    public Paciente registarPaciente(String nombre, String email, String documentoIdentidad, String telefono, Direccion direccion) {
        Paciente paciente = em.persist(new Paciente(null, nombre, email, documentoIdentidad, telefono, true, direccion));
        return paciente;
    }
}