package com.efutures.product.repository;

import com.efutures.product.entity.Category;
import com.efutures.product.entity.Product;
import com.efutures.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    /*@Query(value = "select product_name as productName, product_description as productDescription, category_name as categoryName from category c, product p, product_category pc  where" +
            " c.category_id = pc.category_id and p.product_id = pc.product_category_id and c.category_name = ?1",nativeQuery=true)
    public List<Map<String, Object>>  findProductModelByCategoryName(String categoryName);*/

    @Query(value = "select p.product_Name as product_name, p.product_description as product_description, c.category_name as category_name " +
            "from Category c, Product p, Product_Category pc  where" +
            " c.category_Id = pc.category_Id and p.product_Id = pc.product_Category_Id and c.category_name = ?1",nativeQuery=true)
    public List<ProductModel>  findProductModelByCategoryName(String categoryName);

}
