package pa.com.sura.catalogs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pa.com.sura.catalogs.models.entities.mobilityplatform.CatalogType;
import pa.com.sura.catalogs.repositories.mobilityplatform.CatalogTypeRepository;

import java.util.List;

@Service
public class CatalogTypeServiceImpl implements CatalogTypeService{

    @Autowired
    private CatalogTypeRepository catalogTypeRepository;

    @Override
    //@Transactional(readOnly = true)
    @Transactional(value = "mysqlTransactionManager")
    public List<CatalogType> findAll() {
        return catalogTypeRepository.findAll();
    }
}
