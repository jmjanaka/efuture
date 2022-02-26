package com.efutures.product.enums;

import lombok.Getter;

/**
 * This class for define the product status list as enums
 */
@Getter
public enum ProductStatus {
    ACTIVE("A", "Active"),
    DELETED("D", "Deleted"),
    ;

    private final String code;
    private final String description;

    ProductStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
