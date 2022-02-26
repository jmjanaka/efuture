package com.efutures.product.exception;

public class ProductValidateException extends Exception {
    public ProductValidateException(String errorMessage){
        super(errorMessage);
    }
}
