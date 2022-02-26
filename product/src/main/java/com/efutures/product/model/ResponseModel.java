package com.efutures.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseModel {
    private String status;
    private String message;
    private Object response;

    public ResponseModel(String status, String message, Object response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }
}
