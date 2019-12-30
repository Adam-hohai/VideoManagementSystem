package com.hhuc.classdesign2.javabean;

public class ResultInfo {
    private boolean success;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
