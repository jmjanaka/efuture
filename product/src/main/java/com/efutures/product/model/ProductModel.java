package com.efutures.product.model;

import com.efutures.product.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Getter @Setter

public class ProductModel {
    private String productName;
    private String productDescription;
    private String categoryName;
    private List<Comment> commentList;


}
