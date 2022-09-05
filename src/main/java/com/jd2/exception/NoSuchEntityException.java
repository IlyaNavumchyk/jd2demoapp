package com.jd2.exception;

public class NoSuchEntityException extends RuntimeException{

    private String customMassage;

    private int errorCode;

    private String exceptionId;

    public NoSuchEntityException(String customMassage, int errorCode, String exceptionId) {
        this.customMassage = customMassage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public NoSuchEntityException(String message, String customMassage, int errorCode, String exceptionId) {
        super(message);
        this.customMassage = customMassage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public NoSuchEntityException(String message, Throwable cause, String customMassage, int errorCode, String exceptionId) {
        super(message, cause);
        this.customMassage = customMassage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public NoSuchEntityException(Throwable cause, String customMassage, int errorCode, String exceptionId) {
        super(cause);
        this.customMassage = customMassage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public NoSuchEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String customMassage, int errorCode, String exceptionId) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.customMassage = customMassage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    @Override
    public String toString() {
        return "NoSuchEntityException{" +
                "customMassage='" + customMassage + '\'' +
                ", errorCode=" + errorCode +
                ", exceptionId='" + exceptionId + '\'' +
                '}';
    }
}
