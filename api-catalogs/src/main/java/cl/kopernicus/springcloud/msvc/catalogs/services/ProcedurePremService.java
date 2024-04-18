package cl.kopernicus.springcloud.msvc.catalogs.services;

import cl.kopernicus.springcloud.msvc.catalogs.models.entities.premium.CatalogItem;

import java.util.List;

public interface ProcedurePremService {
    List<CatalogItem> findCatalogs (String catalogo, String filtros);
}
