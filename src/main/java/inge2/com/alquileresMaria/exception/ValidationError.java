package inge2.com.alquileresMaria.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
@Getter @Setter
public class ValidationError {
    private String field;
    private String message;

    public ValidationError(FieldError fieldError) {
        this.field= fieldError.getField(); //Campo que fallo
        this.message = fieldError.getDefaultMessage() != null ?
                        fieldError.getDefaultMessage() : "Error de validacion"; //Mensaje del error
    }
}
