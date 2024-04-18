package cl.kopernicus.springcloud.msvc.catalogs.controllers;

import cl.kopernicus.springcloud.msvc.catalogs.models.errors.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = (Logger) LoggerFactory.getLogger(ExceptionControllerAdvice.class);
    @Autowired
    private HttpServletRequest httpServletRequest;
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exceptionHandler(Exception ex) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(),
                ex,
                httpServletRequest.getRequestURI(),
                httpServletRequest.getHeader("x-trace-id"),
                "");
        PutLogEvents(error);
        return new ResponseEntity<ApiError>(error, HttpStatus.OK);
    }

    public static void PutLogEvents(ApiError Er) {

    }
}