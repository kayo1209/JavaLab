package edu.tdtu._lab4;

public class ResponseApi {
    private int idCode;
    private String message;
    private Object data;

    public ResponseApi(int idCode, String message, Object data) {
        this.idCode = idCode;
        this.message = message;
        this.data = data;
    }

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}