package cl.kopernicus.springcloud.msvc.catalogs.services;

import cl.kopernicus.springcloud.msvc.catalogs.models.entities.premium.CatalogItem;
import cl.kopernicus.springcloud.msvc.catalogs.repositories.premium.ProcedurePremiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedurePremServiceImpl implements ProcedurePremService{

    @Autowired
    ProcedurePremiumRepository procedurePremRepo;

    @Override
    public List<CatalogItem> findCatalogs(String catalogo, String filtros) {
        return procedurePremRepo.findCatalogs(catalogo,filtros);
    }
}
