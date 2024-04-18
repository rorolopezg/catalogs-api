package cl.kopernicus.springcloud.msvc.catalogs.models.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiError implements Serializable {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String trace;
    private String path;
    private String requestId;
    private String documentationUrl;
    private String suggestion;

    private List<ApiSubError> errorsDetails;

    private ApiError() {
        super();
        timestamp = LocalDateTime.now();
        errorsDetails = new ArrayList<>();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.trace = "";
        if (ex != null) {
            String t =  ex.getCause() != null ? ex.getCause().getLocalizedMessage() : "";
            t = t.isEmpty() ? ex.getLocalizedMessage() : "";
            this.trace = t;
        }
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this(status, ex);
        this.message = message;
    }

    public ApiError(HttpStatus status, String message, String path, String requestId, String documentation_url) {
        this(status, message, null);
        this.path = path;
        this.requestId = requestId;
        this.documentationUrl = documentation_url;
        this.trace = "";
    }

    public ApiError(HttpStatus status, String message, Throwable ex, String path, String requestId, String documentation_url) {
        this(status, message, ex);
        this.path = path;
        this.requestId = requestId;
        this.documentationUrl = documentation_url;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public List<ApiSubError> getErrorsDetails() {
        return errorsDetails;
    }

    public void setErrorsDetails(List<ApiSubError> errorsDetails) {
        this.errorsDetails = errorsDetails;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}