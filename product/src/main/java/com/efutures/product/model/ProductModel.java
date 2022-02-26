package com.efutures.product.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@Getter @Setter
@SqlResultSetMapping(
        name = "productModel",
        entities = {
@EntityResult(
        entityClass = ProductModel.class, // The name of the class
        fields = {
                @FieldResult(name = "productName", column = "product_name"),
                @FieldResult(name = "productDescription", column = "product_description"),
                @FieldResult(name = "categoryName", column = "category_name")
        }
)
}
        )

public class ProductModel {
    private String productName;
    private String productDescription;
    private String categoryName;
//    private List<Comment> commentList;


    public ProductModel(String productName, String productDescription, String categoryName) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.categoryName = categoryName;
    }
}
