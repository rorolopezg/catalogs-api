package pa.com.sura.catalogs.services;

import pa.com.sura.catalogs.models.entities.premium.BusinessRules;
import pa.com.sura.catalogs.models.entities.premium.CatalogItem;

import java.util.List;

public interface ProcedurePremService {
    List<CatalogItem> findCatalogs (String catalogo, String filtros);

    BusinessRules findRules(Integer plan);
}
