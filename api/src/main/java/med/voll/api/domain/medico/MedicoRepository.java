package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable pageable);

    @Query("""
        select m from Medico m
        where m.activo = true and
        m.especialidad = :especialidad and
        m.id not in (
        select c.medico.id from Consulta c
        where :fecha = c.fecha)
        order by rand()
        limit 1
    """)
    Medico seleccionarMedicoEspecialidadFecha(LocalDateTime fecha, Especialidad especialidad);

    Boolean existsByIdAndActivoTrue(Long id);
}
