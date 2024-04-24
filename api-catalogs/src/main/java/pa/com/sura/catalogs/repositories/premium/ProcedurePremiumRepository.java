package pa.com.sura.catalogs.repositories.premium;

import org.springframework.jdbc.datasource.DataSourceUtils;
import pa.com.sura.catalogs.models.entities.premium.CatalogItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        //storedProcedure.execute();
        //String pOut = storedProcedure.getParameter("p_ok").getName();
        //String pMensaje = storedProcedure.getParameter("p_mensaje").getName();
        List<CatalogItem> catalogItems = storedProcedure.getResultList().stream().toList();
        return catalogItems;

    }
}
