package com.bank.bankapplication.controller.advice;

import com.bank.bankapplication.data.error.ErrorResponse;
import com.bank.bankapplication.exception.DataAlreadyExistsException;
import com.bank.bankapplication.exception.InsufficientFundsException;
import com.bank.bankapplication.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

import static com.bank.bankapplication.data.error.ErrorType.DATA_ALREADY_EXISTS;
import static com.bank.bankapplication.data.error.ErrorType.INVALID_REQUEST_DATA;
import static com.bank.bankapplication.data.error.ErrorType.NOT_FOUND;
import static com.bank.bankapplication.data.error.ErrorType.UNEXPECTED_ERROR;

@ControllerAdvice
public class GeneralExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.toString())
                .errorKey(NOT_FOUND.name())
                .errorMessage(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .errorKey(INVALID_REQUEST_DATA.name())
                .errorMessage(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleDataAlreadyExistsException(DataAlreadyExistsException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .errorKey(DATA_ALREADY_EXISTS.name())
                .errorMessage(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        String messageId = UUID.randomUUID().toString();
        String errorDescription = "Unexpected system exception ID: " + messageId;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .errorKey(UNEXPECTED_ERROR.name())
                .errorMessage(errorDescription).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .errorKey(INVALID_REQUEST_DATA.name())
                .errorMessage(exception.getBindingResult().getFieldError().getDefaultMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
