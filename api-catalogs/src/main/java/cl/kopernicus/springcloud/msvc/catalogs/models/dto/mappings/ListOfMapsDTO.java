package cl.kopernicus.springcloud.msvc.catalogs.models.dto.mappings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListOfMapsDTO implements Serializable {
    private List<Map<String, String>> objects;

    public ListOfMapsDTO() {
        super();
        objects = new ArrayList<>();
    }

    public ListOfMapsDTO(List<Map<String, String>> objects) {
        this();
        this.objects = objects;
    }

    public List<Map<String, String>> getObjects() {
        return this.objects;
    }

    public void setObjects(List<Map<String, String>> objects) {
        this.objects = objects;
    }

    public Boolean addAll(List<Map<String, String>> objects) {
        return this.objects.addAll(objects);
    }

    public Boolean add(Map<String, String> object) {
        return this.objects.add(object);
    }
}
