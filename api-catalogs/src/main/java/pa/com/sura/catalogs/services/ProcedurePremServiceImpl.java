package pa.com.sura.catalogs.services;

import pa.com.sura.catalogs.models.entities.premium.BusinessRules;
import pa.com.sura.catalogs.models.entities.premium.CatalogItem;
import pa.com.sura.catalogs.repositories.premium.ProcedurePremiumRepository;
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

    @Override
    public BusinessRules findRules(Integer plan) {
        return procedurePremRepo.findRules(plan);
    }
}
