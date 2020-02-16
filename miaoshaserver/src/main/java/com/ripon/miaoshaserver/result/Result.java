package com.ripon.miaoshaserver.result;

public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(CodeMessageEnum.SUCCESS,data);
    }

    public static <T> Result<T> error(CodeMessageEnum codeMessageEnum) {
        return new Result<T>(codeMessageEnum);
    }

    private Result(CodeMessageEnum codeMessageEnum, T data) {
        if(codeMessageEnum != null) {
            this.code = codeMessageEnum.getCode();
            this.message = codeMessageEnum.getMessage();
        }
        this.data = data;
    }


    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(CodeMessageEnum codeMessageEnum) {
        if(codeMessageEnum != null) {
            this.code = codeMessageEnum.getCode();
            this.message = codeMessageEnum.getMessage();
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
