package com.efutures.product.service;

import com.efutures.product.entity.Category;
import com.efutures.product.entity.Comment;
import com.efutures.product.entity.Product;
import com.efutures.product.enums.ProductStatus;
import com.efutures.product.exception.ProductValidateException;
import com.efutures.product.model.ProductModel;
import com.efutures.product.repository.CategoryRepository;
import com.efutures.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product) throws ProductValidateException {
        Optional<Product> fetchedProduct = productRepository.findByProductName(product.getProductName());
        Optional<Category> category = categoryRepository.findById(product.getCategoryId());
        if(!category.isPresent())
            throw new ProductValidateException("Invalid product category");

        Set<Category> setCategory = new HashSet<>();
        setCategory.add(category.get());
        product.setCategories(setCategory);

        if (fetchedProduct.isPresent())
            throw new ProductValidateException("product already exists");
        return productRepository.save(product);
    }

    /**
     * @param product
     * @return
     * @throws ProductValidateException
     */
    @Override
    public Product updateProduct(Product product) throws ProductValidateException {
        Optional<Product> fetchedProductByName = productRepository.findByProductName(product.getProductName());
        if (fetchedProductByName.isPresent()){
            if (fetchedProductByName.get().getProductId().compareTo(product.getProductId()) != 0)
                throw new ProductValidateException("product already exist, please use another product name to update");
        }
        Optional<Product> fetchedProduct = productRepository.findById(product.getProductId());

        if (fetchedProduct.isPresent()){

            Product newProduct = fetchedProduct.get();
            newProduct.setProductName(product.getProductName());
            newProduct.setProductDescription(product.getProductDescription());
            newProduct.setPrice(product.getPrice());
            newProduct.setStatus(product.getStatus());

            productRepository.save(newProduct);


        }
        return productRepository.save(product);
    }

    /**
     * @param productId
     * @return
     */
    @Override
    @Transactional
    public void deleteProduct(Long productId, String comment) throws ProductValidateException {
        if (comment == null || comment.isEmpty())
            throw new ProductValidateException("comment must be entered");

        Optional<Product> fetchedProduct = productRepository.findById(productId);

        if (fetchedProduct.isPresent()){
            if (fetchedProduct.get().getStatus().equals(ProductStatus.DELETED.getCode()))
                throw new ProductValidateException("Already deleted the product");
            Product newProduct = fetchedProduct.get();
            Comment newComment = new Comment();
            newComment.setComment(comment);
            newComment.setProduct(fetchedProduct.get());
            newComment.setCreatedTime(new Date());
            Set<Comment> setComment = new HashSet<>();
            setComment.add(newComment);

            newProduct.setStatus(ProductStatus.DELETED.getCode());
            newProduct.setComment(setComment);
            productRepository.save(newProduct);
            productRepository.flush();
        }else
            throw new ProductValidateException("Unable to find matching record");
    }

    /**
     * @param categoryName
     * @return
     * @throws ProductValidateException
     */
    @Override
    public List<ProductModel> getProductListByCategoryName(String categoryName) throws ProductValidateException {
        if (categoryName == null || categoryName.isEmpty())
            throw new ProductValidateException("product category must be entered.");

        return productRepository.getProductListByCategoryName(categoryName);
    }


}