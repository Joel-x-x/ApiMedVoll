package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private String distrito;
    private Integer numero;
    private String complemento;
    private String ciudad;

    public Direccion(DatosDireccion datosDireccion) {
        this.calle = datosDireccion.calle();
        this.distrito = datosDireccion.distrito();
        this.numero = datosDireccion.numero();
        this.complemento = datosDireccion.complemento();
        this.ciudad = datosDireccion.ciudad();
    }

    public void actualizarDatos(DatosDireccion datosDireccion) {
        this.calle = datosDireccion.calle();
        this.distrito = datosDireccion.distrito();
        this.numero = datosDireccion.numero();
        this.complemento = datosDireccion.complemento();
        this.ciudad = datosDireccion.ciudad();
    }
}
