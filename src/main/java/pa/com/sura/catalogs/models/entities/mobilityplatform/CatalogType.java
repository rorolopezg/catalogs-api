package pa.com.sura.catalogs.models.entities.mobilityplatform;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "catalog_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogType implements Serializable {

    @Id
    private Long id;
    private String name;
    private String description;

}
