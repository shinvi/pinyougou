package com.pinyougou.web.common.exception;


import com.pinyougou.web.common.ResponseCode;

public class ResponseException extends RuntimeException {
    private final ResponseCode responseCode;
    private ResponseCode extrasCode;

    public ResponseException(ResponseCode responseCode) {
        super(responseCode.getDesc());
        this.responseCode = responseCode;
    }

    public ResponseException(String message) {
        super(message);
        this.responseCode = ResponseCode.ERROR.setDesc(message);
    }

    public ResponseCode getExtrasCode() {
        return extrasCode;
    }

    public ResponseException setExtrasCode(ResponseCode extrasCode) {
        this.extrasCode = extrasCode;
        return this;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
