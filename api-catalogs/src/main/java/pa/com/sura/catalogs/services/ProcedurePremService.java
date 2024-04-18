package pa.com.sura.catalogs.services;

import pa.com.sura.catalogs.models.entities.premium.CatalogItem;

import java.util.List;

public interface ProcedurePremService {
    List<CatalogItem> findCatalogs (String catalogo, String filtros);
}
