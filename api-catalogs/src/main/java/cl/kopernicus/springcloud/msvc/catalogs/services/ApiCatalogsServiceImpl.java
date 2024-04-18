package cl.kopernicus.springcloud.msvc.catalogs.services;

import cl.kopernicus.springcloud.msvc.catalogs.models.entities.MapTable;
import cl.kopernicus.springcloud.msvc.catalogs.repositories.CatalogRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@Service
public class ApiCatalogsServiceImpl implements ApiCatalogsService {
    private static final Logger log = LoggerFactory.getLogger(ApiCatalogsServiceImpl.class);
    @Autowired
    private CatalogRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<MapTable> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<MapTable>> findByAtoB(
            String mappingType, String systemA, String systemB, String codeA) {
        return repository.findByAtoB(mappingType, systemA, systemB, codeA);
    }

    @Override
    public Page<MapTable> findByAtoB(String mappingType, String systemA, String systemB, List<String> codes, Pageable pageable) {
        return repository.findByAtoB(mappingType, systemA, systemB, codes, pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<List<MapTable>> findByBtoA(
            String mappingType, String systemB, String systemA, String codeB) {
        return repository.findByBtoA(mappingType, systemB, systemA, codeB );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MapTable> findByBtoA(String mappingType, String systemB, String systemA, List<String> codes, Pageable pageable) {
        return repository.findByBtoA(mappingType, systemB, systemA, codes, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<MapTable>> findAllMapCoincidences(
            String mappingType, String systemA, String systemB, String code) {
        return repository.findAllMapCoincidences(mappingType, systemA, systemB, code);
    }

    @Override
    public Page<MapTable> findAllMapCoincidences(String mappingType, String systemA, String systemB, List<String> codes, Pageable pageable) {
        return repository.findAllMapCoincidences(mappingType, systemA, systemB, codes, pageable);
    }
}
