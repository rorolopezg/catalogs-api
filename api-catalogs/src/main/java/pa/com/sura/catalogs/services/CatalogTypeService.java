package pa.com.sura.catalogs.services;

import pa.com.sura.catalogs.models.entities.mobilityplatform.CatalogType;

import java.util.List;

public interface CatalogTypeService {

    List<CatalogType> findAll();

}
