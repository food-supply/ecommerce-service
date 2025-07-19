package com.se.ecommerce_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "errors")
public class ErrorMessagesProperties {
    private Map<String, ErrorDetail> errorMap = new HashMap<>();

    public ErrorDetail getError(String key) {
        return errorMap.getOrDefault(key, defaultError());
    }

    private ErrorDetail defaultError() {
        ErrorDetail error = new ErrorDetail();
        error.setCode(9999);
        error.setMessage("Lỗi không xác định");
        error.setMessageDetail("Vui lòng liên hệ bộ phận hỗ trợ");
        return error;
    }
    
    public static class ErrorDetail {
        private Integer code;
        private String message;
        private String messageDetail;

        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public String getMessageDetail() {
            return messageDetail;
        }
        public void setMessageDetail(String messageDetail) {
            this.messageDetail = messageDetail;
        }
        
    }

}
