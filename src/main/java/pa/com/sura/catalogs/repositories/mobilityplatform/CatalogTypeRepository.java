package pa.com.sura.catalogs.repositories.mobilityplatform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pa.com.sura.catalogs.models.entities.mobilityplatform.CatalogType;
@Repository
public interface CatalogTypeRepository extends JpaRepository<CatalogType,Long> {


}
