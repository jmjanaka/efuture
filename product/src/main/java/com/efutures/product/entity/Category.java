package com.efutures.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "category")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @ManyToMany(mappedBy = "categories", cascade = { CascadeType.ALL })
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    public void setProducts(Set<Product> products){
        for (Product product : products){
            product.getCategories().add(this);
            products.add(product);
        }
    }

    public Set<Product> getProducts(){
        return products;
    }

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    public Category(Set<Product> products,
                    String categoryName,
                    String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        setProducts(products);
    }
}
