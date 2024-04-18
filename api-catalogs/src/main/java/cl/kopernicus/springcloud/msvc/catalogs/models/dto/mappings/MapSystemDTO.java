package cl.kopernicus.springcloud.msvc.catalogs.models.dto.mappings;

import java.io.Serializable;
import java.util.Objects;

public class MapSystemDTO implements Serializable {
    private String systemName;
    private String systemType;
    private String code;
    private String value;

    private String positionType;

    public MapSystemDTO() {
        super();
    }

    public MapSystemDTO(String systemName, String systemType, String code, String value, String positionType) {
        this();
        this.systemName = systemName;
        this.systemType = systemType;
        this.code = code;
        this.value = value;
        this.positionType = positionType;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapSystemDTO mapSystem = (MapSystemDTO) o;
        return Objects.equals(systemName, mapSystem.systemName) && Objects.equals(systemType, mapSystem.systemType) && Objects.equals(code, mapSystem.code) && Objects.equals(value, mapSystem.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName, systemType, code, value);
    }
}
