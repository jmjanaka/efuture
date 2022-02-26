package com.efutures.product.repository;

import com.efutures.product.entity.Category;
import com.efutures.product.entity.Product;
import com.efutures.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findByProductName(String productName);

    public List<Product> findByCategories(Category category);




    /*@Query(value = "select * from category c, product p, product_category pc  where c.category_id = pc.category_id and " +
            "p.product_id = pc.product_category_id and c.name = ?1", nativeQuery = true);
    public List<ProductModel> getProductListByCategoryName(String categoryName);*/

    /*@Query("SELECT new com.efutures.product.model.ProductModel(p.name , p.description, c.name) " +
            "from category c, product p, product_category pc  where" +
            " c.category_id = pc.category_id and p.product_id = pc.product_category_id and c.name = ?1")
    public List<ProductModel> getProductListByCategoryName(String categoryName);*/


    @Query(value = "select product_name, product_description, category_name from category c, product p, product_category pc  where" +
            " c.category_id = pc.category_id and p.product_id = pc.product_category_id and c.category_name = ?1",nativeQuery=true)
    public List<ProductModel> getProductListByCategoryName(String categoryName);

}
