package com.efutures.product.model;

import com.efutures.product.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter

public class ProductModel {
    private String productName;
    private String productDescription;
    private String categoryName;
    private BigDecimal price;
    private String productStatus;
    private List<Comment> commentList;


}
