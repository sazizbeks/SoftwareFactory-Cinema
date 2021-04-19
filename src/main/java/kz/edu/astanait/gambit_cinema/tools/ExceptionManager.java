package kz.edu.astanait.gambit_cinema.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.LinkedList;
import java.util.List;

/**
 * Converts exception to messages for API
 */
public class ExceptionManager {
    public static ResponseEntity<?> getResponseEntity(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(toList(e));
    }

    public static ResponseEntity<?> getResponseEntity(HttpStatus status, BindingResult bindingResult) {
        return ResponseEntity.status(status).body(toList(bindingResult));
    }

    private static ListOfExceptionMessages toList(BindingResult bindingResult) {
        List<String> messages = new LinkedList<>();
        bindingResult.getFieldErrors()
                .forEach(fieldError ->
                        messages.add(fieldError.getDefaultMessage()));
        return new ListOfExceptionMessages(messages);
    }

    private static ListOfExceptionMessages toList(Exception e) {
        List<String> messages = new LinkedList<>();
        messages.add(e.getMessage());
        return new ListOfExceptionMessages(messages);
    }
}
