package com.entiy;

public class SysLogEntity {

    private String operation;
    private String method;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "SysLogEntity{" +
                "operation='" + operation + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
