package inha.capstone.fooda.domain.common.response;

import inha.capstone.fooda.domain.common.exception.CustomException;
import inha.capstone.fooda.domain.common.exception.ExceptionType;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandle(CustomException e) {
        log.error("CustomException: {}", String.valueOf(e));

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(e.getErrorCode(), e.getErrorMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", String.valueOf(e));

        int errorCode = ExceptionType.BAD_REQUEST_EXCEPTION.getErrorCode();
        String errorMessage = ExceptionType.BAD_REQUEST_EXCEPTION.getErrorMessage() + " " + e.getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(errorCode, errorMessage));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> methodMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException: {}", String.valueOf(e));

        int errorCode = ExceptionType.BAD_REQUEST_EXCEPTION.getErrorCode();
        String errorMessage = "파일 최대 크기 초과";

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(errorCode, errorMessage));
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationExceptionHandle(ConstraintViolationException e) {
        log.error("ConstraintViolationException: {}", String.valueOf(e));

        int errorCode = ExceptionType.BAD_REQUEST_EXCEPTION.getErrorCode();
        String errorMessage = ExceptionType.BAD_REQUEST_EXCEPTION.getErrorMessage() + " " + e.getLocalizedMessage();

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(errorCode, errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandle(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error("UnHandled Exception: {} info=" + "{}:{}:{}", e,
                stackTrace[0].getClassName(), stackTrace[0].getMethodName(), stackTrace[0].getLineNumber());

        int errorCode = ExceptionType.UNHANDLED_EXCEPTION.getErrorCode();
        String errorMessage = ExceptionType.UNHANDLED_EXCEPTION.getErrorMessage();

        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse(errorCode, errorMessage));
    }
}