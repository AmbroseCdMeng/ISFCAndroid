package com.maci.foxconn.isfcandroid;

import java.io.Serializable;
import java.util.List;

public class Beans implements Serializable {

    private boolean status;
    private String message;
    private Object result;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
