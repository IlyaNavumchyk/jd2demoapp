package com.jd2.exception.exceptionhandle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorContainer {

    private String clazz;

    private String exceptionId;

    private String errorMessage;

    private int errorCode;

    private String stackTrace;

    public static String getStackTrace (Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            sb.append(stackTraceElement.toString()).append('\n');
        }
        return sb.toString();
    }
}
