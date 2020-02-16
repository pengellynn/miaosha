package com.ripon.miaoshaserver.result;

public enum CodeMessageEnum {

    SUCCESS(200, "success"),
    SERVER_ERROR(500100, "服务端异常"),
    BIND_ERROR(500101, "参数检验错误: %s"),
    REQUEST_ILLEGAL(500102,"请求非法"),
    VERIFY_CODE_ERROR(500103, "验证码错误"),

    SESSION_ERROR(500210, "session不存在或已失效"),
    MOBLIE_NOT_EXIST(500211, "账号不存在"),
    MOBILE_ERROR(500212, "登录密码不能为空"),
    PASSWORD_ERROR(500213, "密码错误"),
    REGISTER_FAIL(500214, "注册失败"),
    MIAOSHA_OVER(500410, "秒杀结束"),
    REPEAT_MIAOSHA(500411, "重复秒杀"),
    MIAOSHA_FAIL(500412, "秒杀失败"),

    CREATE_ORDER_FAIL(500510, "创建订单失败");


    private int code;
    private String message;

    private CodeMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CodeMessageEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
