package com.efutures.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseModel {
    private String status;
    private String message;
    private Object responseObject;

    public ResponseModel(String status, String message, Object responseObject) {
        this.status = status;
        this.message = message;
        this.responseObject = responseObject;
    }
}
