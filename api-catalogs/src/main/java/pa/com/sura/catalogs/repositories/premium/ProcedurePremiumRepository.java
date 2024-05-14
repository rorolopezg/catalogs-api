package pa.com.sura.catalogs.repositories.premium;

import jakarta.transaction.Transactional;
import org.springframework.jdbc.datasource.DataSourceUtils;
import pa.com.sura.catalogs.models.entities.premium.BusinessRules;
import pa.com.sura.catalogs.models.entities.premium.CatalogItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProcedurePremiumRepository {

    @Autowired
    @Qualifier("oracleEntityManagerFactory")
    private EntityManager entityManager;

    public List<CatalogItem> findCatalogs(String catalogType, String filters) {

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("SEGUROS.pkg_nube_seguros2.catalogos", CatalogItem.class);
        storedProcedure.registerStoredProcedureParameter("p_tabla", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_parametro", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_ok", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_mensaje", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_info", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("p_tabla", catalogType);
        storedProcedure.setParameter("p_parametro", filters);
        List<CatalogItem> catalogItems = storedProcedure.getResultList().stream().toList();
        return catalogItems;

    }


    public BusinessRules findRules(Integer plan) {

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("SEGUROS.pkg_nube_seguros2.reglas_de_negocio_automovil", BusinessRules.class);
        storedProcedure.registerStoredProcedureParameter("p_plan", Integer.class, ParameterMode.IN);
        storedProcedure.setParameter("p_plan", plan);
        storedProcedure.registerStoredProcedureParameter("p_descripcion", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_beneficios", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_filtro_por_marcas", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_filtro_por_tipo_chasis", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_filtro_por_acreedores", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_excluir_modelos", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_bloquear_limites", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_suma_asegurada_minima", Integer.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_suma_asegurada_maxima", Integer.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_ano", Integer.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_ok", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_mensaje", String.class, ParameterMode.OUT);

        storedProcedure.execute();
        String ok = (String) storedProcedure.getOutputParameterValue("p_ok");
        if ("S".equals(ok)) {
            BusinessRules rules = new BusinessRules();
            rules.setDescripcion((String) storedProcedure.getOutputParameterValue("p_descripcion"));
            rules.setBeneficios((String) storedProcedure.getOutputParameterValue("p_beneficios"));
            rules.setFiltroPorMarcas((String) storedProcedure.getOutputParameterValue("p_filtro_por_marcas"));
            rules.setFiltroPorTipoChasis((String) storedProcedure.getOutputParameterValue("p_filtro_por_tipo_chasis"));
            rules.setFiltroPorAcreedores((String) storedProcedure.getOutputParameterValue("p_filtro_por_acreedores"));
            rules.setExcluirModelos((String) storedProcedure.getOutputParameterValue("p_excluir_modelos"));
            rules.setBloquearLimites((String) storedProcedure.getOutputParameterValue("p_bloquear_limites"));
            rules.setSumaAseguradaMinima((Integer) storedProcedure.getOutputParameterValue("p_suma_asegurada_minima"));
            rules.setSumaAseguradaMaxima((Integer) storedProcedure.getOutputParameterValue("p_suma_asegurada_maxima"));
            rules.setAno((Integer) storedProcedure.getOutputParameterValue("p_ano"));
            return rules;
        } else {
            // Manejar el caso de error
            String mensaje = (String) storedProcedure.getOutputParameterValue("p_mensaje");
            throw new RuntimeException("Error al consultar el plan: " + mensaje);
        }
    }
}
