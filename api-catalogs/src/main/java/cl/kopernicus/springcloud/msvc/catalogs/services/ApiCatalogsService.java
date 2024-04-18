package cl.kopernicus.springcloud.msvc.catalogs.services;

import cl.kopernicus.springcloud.msvc.catalogs.models.entities.MapTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApiCatalogsService {
    //Page<Usuario> listar(Pageable pageable);
    Page<MapTable> findAll(Pageable pageable);
    Optional<List<MapTable>> findByAtoB(String mappingType, String systemA, String systemB, String codeA);

    Page<MapTable> findByAtoB(String mappingType, String systemA, String systemB, List<String> codes, Pageable pageable);
    Optional<List<MapTable>> findByBtoA(String mappingType, String systemB, String systemA, String bCode);

    Page<MapTable> findByBtoA(String mappingType, String systemB, String systemA, List<String> codes, Pageable pageable);

    Optional<List<MapTable>> findAllMapCoincidences(String mappingType,String systemA, String systemB, String code);

    Page<MapTable> findAllMapCoincidences(
            String mappingType,
            String systemA,
            String systemB,
            List<String> codes,
            Pageable pageable
    );
}
