package kz.edu.astanait.gambit_cinema.tools;

import org.springframework.validation.BindingResult;

import java.util.LinkedList;
import java.util.List;

public class ExceptionConverter {
    public static ListOfExceptionMessages convertValidationError(BindingResult bindingResult) {
        List<String> messages = new LinkedList<>();
        bindingResult.getFieldErrors()
                .forEach(fieldError ->
                        messages.add(fieldError.getDefaultMessage()));
        return new ListOfExceptionMessages(messages);
    }
}
