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

    private StackTraceElement[] stackTrace;
}
