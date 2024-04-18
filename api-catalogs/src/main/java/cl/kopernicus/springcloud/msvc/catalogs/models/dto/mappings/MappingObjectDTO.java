package cl.kopernicus.springcloud.msvc.catalogs.models.dto.mappings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MappingObjectDTO implements Serializable {
    private Long id;
    private String mappingType;

    private List<MapSystemDTO> mappings;

    public MappingObjectDTO() {
        super();
        mappings = new ArrayList<>();
    }

    public MappingObjectDTO(MapSystemDTO systemA, MapSystemDTO systemB) {
        this();
        mappings.add(systemA);
        mappings.add(systemB);
    }

    public MappingObjectDTO(Long id, String mappingType, MapSystemDTO systemA, MapSystemDTO systemB) {
        this(systemA, systemB);
        this.id = id;
        this.mappingType = mappingType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMappingType() {
        return mappingType;
    }

    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }

    public List<MapSystemDTO> getMappings() {
        return mappings;
    }

    public void setMappings(List<MapSystemDTO> mappings) {
        this.mappings = mappings;
    }
}
