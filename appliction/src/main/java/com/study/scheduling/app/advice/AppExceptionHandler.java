package com.study.scheduling.app.advice;

import com.study.scheduling.app.exception.AppException;
import com.study.scheduling.app.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleArgumentException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({ AppException.class, HttpMessageNotReadableException.class })
    public ResponseEntity handleException(Exception exception) {
        String message = AppConstants.INVALID_POST_MSG;
        if(exception instanceof AppException) {
            message = ((AppException) exception).getErrorMessage();
        }

        return ResponseEntity.badRequest().body(Arrays.asList(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleUnknownException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppConstants.INTERNAL_SERVER_ERROR_MSG);
    }

}
