package com.efutures.product.entity;

import com.efutures.product.model.AuditModel;
import com.efutures.product.entity.Category;
import com.efutures.product.entity.Comment;
import com.efutures.product.model.ProductModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "product_category",
            joinColumns = {
            @JoinColumn(name = "product_category_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "category_id")
            })
    Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<Comment> comment;

    @NotBlank(message = "Name is mandatory")
    @Column(name ="product_name")
    private String productName;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "product_description")
    private String productDescription;

//    @NotEmpty(message = "Price is mandatory")
//    @Pattern(regexp="^[+-]?([0-9]+\\.?[0-9]*|\\.[0-9]+)$", message="Enter valid price")
    @Digits(integer = 17, fraction = 2, message="Enter valid price")
    @Column(name = "price")
    private BigDecimal price;

    @NotBlank(message = "status is mandatory")
    @Column(name = "status")
    private String status;

    @Column(name = "launchDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date launchDate;

    @Transient
//    @NotBlank(message = "product category id is mandatory")
    private Long categoryId;

    public Product(String productName,
                   String productDescription,
                   BigDecimal price,
                   String status,
                   Date launchDate) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.status = status;
        this.launchDate = launchDate;
    }



}
