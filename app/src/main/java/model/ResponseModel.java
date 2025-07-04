package model;

import com.google.gson.annotations.SerializedName;


public class ResponseModel {
    private boolean success;
    @SerializedName("msg") // 匹配后端返回的 "msg" 字段
    private String message;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
