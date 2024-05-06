package pa.com.sura.catalogs.models.entities.premium;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CatalogItem {

    @Id
    private String codigo;
    private String nombre;

}
