package pa.com.sura.catalogs.models.entities.premium;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Entity
@Table(name = "i_planes")
@Getter
@Setter
public class BusinessRules implements Serializable {
    @Id
    @Column(name = "plan")
    private Integer plan;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "beneficios")
    private String beneficios;

    @Column(name = "filtro_por_marcas")
    private String filtroPorMarcas;

    @Column(name = "filtro_por_tipo_chasis")
    private String filtroPorTipoChasis;

    @Column(name = "filtro_por_acreedores")
    private String filtroPorAcreedores;

    @Column(name = "excluir_modelos")
    private String excluirModelos;

    @Column(name = "bloquear_limites")
    private String bloquearLimites;

    @Column(name = "suma_asegurada_minima")
    private Integer sumaAseguradaMinima;

    @Column(name = "suma_asegurada_maxima")
    private Integer sumaAseguradaMaxima;

    @Column(name = "ano")
    private Integer ano;
}
