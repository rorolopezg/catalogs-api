package cl.kopernicus.springcloud.msvc.catalogs.models.errors;

public class ApiGeneralError extends ApiSubError {
    private String code;
    private String message;

    private String suggestion;
    private String documentation;
}