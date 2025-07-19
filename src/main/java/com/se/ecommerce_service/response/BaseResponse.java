package com.se.ecommerce_service.response;

public class BaseResponse <T> {
    private String requestId;
    private Integer errorCode;
    private String message;
    private String messageDetail;
    private T data;

    private BaseResponse(Builder<T> builder) {
        this.requestId = builder.requestId;
        this.errorCode = builder.errorCode;
        this.message = builder.message;
        this.messageDetail = builder.messageDetail;
        this.data = builder.data;
    }

    public static class Builder<T> {
        private String requestId;
        private Integer errorCode;
        private String message;
        private String messageDetail;
        private T data;

        public Builder<T> requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder<T> errorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> messageDetail(String messageDetail) {
            this.messageDetail = messageDetail;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public BaseResponse<T> build() {
            return new BaseResponse<>(this);
        }
    }
    
    public BaseResponse(String requestId, Integer errorCode, String message, String messageDetail, T data) {
        this.requestId = requestId;
        this.errorCode = errorCode;
        this.message = message;
        this.messageDetail = messageDetail;
        this.data = data;
    }
    public BaseResponse() {
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public Integer getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
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
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    
}
