package com.se.ecommerce_service.exception;

import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.se.ecommerce_service.config.ErrorMessagesProperties;
import com.se.ecommerce_service.response.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorMessagesProperties errorMessages;

    public GlobalExceptionHandler(ErrorMessagesProperties errorMessages) {
        this.errorMessages = errorMessages;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<String>> handleBusinessException(BusinessException ex) {
        var error = errorMessages.getError(ex.getErrorKey());

        return ResponseEntity.badRequest().body(
            new BaseResponse.Builder<String>()
                .requestId(MDC.get("request_id"))
                .errorCode(error.getCode())
                .message(error.getMessage())
                .messageDetail(ex.getMessage())
                .data(null)
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError().body(
            new BaseResponse.Builder<String>()
                .requestId(MDC.get("request_id"))
                .errorCode(9999)
                .message("Lỗi không xác định")
                .messageDetail(ex.getMessage())
                .data(null)
                .build()
        );
    }
}
