package com.ripon.miaoshaserver.exception;

import com.ripon.miaoshaserver.result.CodeMessageEnum;
import com.ripon.miaoshaserver.result.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCodeMessageEnum());
        }else if (e instanceof BindException) {
            return Result.error(CodeMessageEnum.BIND_ERROR);
        } else if (e instanceof MethodArgumentNotValidException){
            return Result.error(CodeMessageEnum.BIND_ERROR);
        }
        else {
            return Result.error(CodeMessageEnum.SERVER_ERROR);
        }
    }
}
