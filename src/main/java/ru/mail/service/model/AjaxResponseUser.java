package ru.mail.service.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.service.jsonview.Views;

public class AjaxResponseUser {

    @JsonView(Views.Public.class)
    private
    String msg;
    @JsonView(Views.Public.class)
    private
    String code;
    @JsonView(Views.Public.class)
    private
    User result;

    public AjaxResponseUser(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AjaxResponseResult [msg=" + msg + ", code=" + code + ", result=" + result + "]";
    }

}
