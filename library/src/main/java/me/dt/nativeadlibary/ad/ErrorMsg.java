package me.dt.nativeadlibary.ad;


/**
 * Created by joybar on 2017/5/3.
 */

public class ErrorMsg<T> {


    private String errorMsg;
    private Object extraErrorData;


    public String getErrorMsg() {
        return errorMsg;
    }


    public Object getExtraErrorData() {
        return extraErrorData;
    }

    public ErrorMsg() {
    }

    public ErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ErrorMsg(String errorMsg, Object extraErrorData) {
        this.errorMsg = errorMsg;
        this.extraErrorData = extraErrorData;
    }


}
