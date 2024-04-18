package cl.kopernicus.springcloud.msvc.catalogs.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueSystemA", columnNames = {"mapping_type", "system_a", "code_a", "system_b"}),
        @UniqueConstraint(name = "UniqueSystemB", columnNames = {"mapping_type", "system_b", "code_b", "system_a"})
})
public class MapTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mapping_type", length = 32)
    private String mappingType;
    @Column(name = "system_a", length = 32)
    private String systemA;
    @Column(name = "system_a_type", length = 16)
    private String systemAType;
    @Column(name = "code_a", length = 32)
    private String codeA;
    @Column(name = "name_a")
    private String nameA;
    @Column(name = "system_b", length = 32)
    private String systemB;
    @Column(name = "system_b_type", length = 16)
    private String systemBType;
    @Column(name = "code_b", length = 32)
    private String codeB;
    @Column(name = "name_b")
    private String nameB;

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

    public String getSystemA() {
        return systemA;
    }

    public void setSystemA(String systemA) {
        this.systemA = systemA;
    }

    public String getSystemAType() {
        return systemAType;
    }

    public void setSystemAType(String systemAType) {
        this.systemAType = systemAType;
    }

    public String getCodeA() {
        return codeA;
    }

    public void setCodeA(String codeA) {
        this.codeA = codeA;
    }

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public String getSystemB() {
        return systemB;
    }

    public void setSystemB(String systemB) {
        this.systemB = systemB;
    }

    public String getSystemBType() {
        return systemBType;
    }

    public void setSystemBType(String systemBType) {
        this.systemBType = systemBType;
    }

    public String getCodeB() {
        return codeB;
    }

    public void setCodeB(String codeB) {
        this.codeB = codeB;
    }

    public String getNameB() {
        return nameB;
    }

    public void setNameB(String nameB) {
        this.nameB = nameB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapTable mapTable = (MapTable) o;
        return Objects.equals(id, mapTable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
