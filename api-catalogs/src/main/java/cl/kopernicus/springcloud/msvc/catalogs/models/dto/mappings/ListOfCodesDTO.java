package cl.kopernicus.springcloud.msvc.catalogs.models.dto.mappings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfCodesDTO implements Serializable {
    private List<String> codes;

    public ListOfCodesDTO() {
        super();
        codes = new ArrayList<>();
    }

    public ListOfCodesDTO(List<String> codes) {
        this();
        this.codes = codes;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Boolean addAll(List<String> codes) {
        return this.codes.addAll(codes);
    }

    public Boolean add(String code) {
        return this.codes.add(code);
    }
}
