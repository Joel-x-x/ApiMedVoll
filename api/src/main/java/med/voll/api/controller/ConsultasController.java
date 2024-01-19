package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultasController {

    @Autowired
    private AgendarConsultaService agendarConsultaService;

    @Autowired
    private CancelarConsultaService cancelarConsultaService;

    @PostMapping
    @Operation(
            summary = "Registra una consulta en la base de datos",
            description = "",
            tags = {"consulta","post"}
    )
    public ResponseEntity<DatosDetalleConsulta> agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta) {
        Consulta consulta = agendarConsultaService.agendar(datosAgendarConsulta);

        return ResponseEntity.ok(new DatosDetalleConsulta(consulta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Cancela una consulta de la agenda",
            description = "",
            tags = {"consulta", "delete"}
    )
    public ResponseEntity<String> cancelar(@RequestBody @Valid DatosCancelacionConsulta datosCancelacionConsulta, @PathVariable Long id) {
        datosCancelacionConsulta = new DatosCancelacionConsulta(datosCancelacionConsulta.motivoCancelacion(), id);

        Consulta consulta = cancelarConsultaService.cancelarConsulta(datosCancelacionConsulta);

        return ResponseEntity.ok("Cita cancelada exitosamente motivo de cancelación: " + consulta.getMotivoCancelacion());
    }
}
