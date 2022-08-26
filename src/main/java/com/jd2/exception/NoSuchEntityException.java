package com.jd2.exception;

public class NoSuchEntityException extends RuntimeException{

    private String customMassage;

    private int errorCode;

    public NoSuchEntityException(String customMassage, int errorCode) {
        this.customMassage = customMassage;
        this.errorCode = errorCode;
    }

    public NoSuchEntityException(String message, String customMassage, int errorCode) {
        super(message);
        this.customMassage = customMassage;
        this.errorCode = errorCode;
    }

    public NoSuchEntityException(String message, Throwable cause, String customMassage, int errorCode) {
        super(message, cause);
        this.customMassage = customMassage;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "NoSuchEntityException{" +
                "customMassage='" + customMassage + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
