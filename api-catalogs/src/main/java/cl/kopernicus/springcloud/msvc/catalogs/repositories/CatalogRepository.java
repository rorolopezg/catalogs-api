package cl.kopernicus.springcloud.msvc.catalogs.repositories;

import cl.kopernicus.springcloud.msvc.catalogs.models.entities.MapTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<MapTable, Long> {
    /* Obtiene mapeos específicos de un catálogo, sistema A, código A y sistema B */
    @Query("select mt from MapTable mt where mt.mappingType = ?1 and mt.systemA = ?2 and mt.systemB = ?3 and mt.codeA = ?4")
    Optional<List<MapTable>> findByAtoB(
            String mappingType,
            String systemA,
            String systemB,
            String codeA
    );

    @Query( "select mt from MapTable mt " +
            "where mt.mappingType = ?1 and mt.systemA = ?2 and mt.systemB = ?3 " +
            "and mt.codeA in (?4)"
    )
    Page<MapTable> findByAtoB(
            String mappingType,
            String systemA,
            String systemB,
            List<String> codes,
            Pageable pageable
    );

    /* Obtiene mapeos específicos de un catálogo, sistema B, código B y sistema A */
    @Query("select mt from MapTable mt where mt.mappingType = ?1 and mt.systemB = ?2 and mt.systemA = ?3 and mt.codeB = ?4")
    Optional<List<MapTable>> findByBtoA(
            String mappingType,
            String systemB,
            String systemA,
            String codeB
    );

    @Query( "select mt from MapTable mt " +
            "where mt.mappingType = ?1 and mt.systemB = ?2 and mt.systemA = ?3 " +
            "and mt.codeB in (?4)"
    )
    Page<MapTable> findByBtoA(
            String mappingType,
            String systemB,
            String systemA,
            List<String> codes,
            Pageable pageable
    );

    @Query(
            "select mt from MapTable mt where mt.mappingType = ?1 and mt.systemA = ?2 and mt.systemB = ?3 and (mt.codeA = ?4 OR mt.codeB = ?4)" +
            "union " +
            "select mt from MapTable mt where mt.mappingType = ?1 and mt.systemB = ?2 and mt.systemA = ?3 and (mt.codeA = ?4 OR mt.codeB = ?4)"
    )
    Optional<List<MapTable>> findAllMapCoincidences(
            String mappingType,
            String systemA,
            String systemB,
            String code
    );

    @Query(
            "select mt from MapTable mt where mt.mappingType = ?1 and mt.systemA = ?2 and mt.systemB = ?3 and (mt.codeA in (?4) OR mt.codeB in (?4))" +
            "union " +
            "select mt from MapTable mt where mt.mappingType = ?1 and mt.systemB = ?2 and mt.systemA = ?3 and (mt.codeA in (?4) OR mt.codeB in (?4))"
    )
    Page<MapTable> findAllMapCoincidences(
            String mappingType,
            String systemA,
            String systemB,
            List<String> codes,
            Pageable pageable
    );
}
