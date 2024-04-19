package pa.com.sura.catalogs.repositories.premium;

import pa.com.sura.catalogs.models.entities.premium.CatalogItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class ProcedurePremiumRepository {

    @Autowired
    @Qualifier("oracleEntityManagerFactory")
    private EntityManager entityManager;

    public List<CatalogItem> findCatalogs (String catalogo, String filtros){

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("SEGUROS.pkg_nube_seguros.catalogos", CatalogItem.class);
        storedProcedure.registerStoredProcedureParameter("p_tabla", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_parametro", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_ok", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_mensaje", String.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("p_info", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("p_tabla", catalogo);
        storedProcedure.setParameter("p_parametro", filtros);

        List<CatalogItem> catalogItems = storedProcedure.getResultList().stream().toList();

        return catalogItems;

    }

}
