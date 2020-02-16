package com.ripon.miaoshaserver.exception;

import com.ripon.miaoshaserver.result.CodeMessageEnum;

public class GlobalException extends RuntimeException {
    private CodeMessageEnum codeMessageEnum;

    public GlobalException(CodeMessageEnum codeMessageEnum) {
        this.codeMessageEnum = codeMessageEnum;
    }

    public CodeMessageEnum getCodeMessageEnum() {
        return this.codeMessageEnum;
    }
}
