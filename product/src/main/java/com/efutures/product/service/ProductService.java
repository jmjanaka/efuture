package com.efutures.product.service;

import com.efutures.product.exception.ProductValidateException;
import com.efutures.product.entity.Product;
import com.efutures.product.model.ProductModel;

import java.util.List;

public interface ProductService {
    /**
     *
     * @param product
     * @return
     * @throws ProductValidateException
     */
    public Product createProduct(Product product) throws ProductValidateException;

    /**
     *
     * @param product
     * @return
     * @throws ProductValidateException
     */
    public Product updateProduct(Product product) throws ProductValidateException;

    /**
     *
     * @param productId
     * @return
     */
    public void deleteProduct(Long productId, String comment) throws ProductValidateException;

    /**
     *
     * @param categoryName
     * @return
     * @throws ProductValidateException
     */
    public List<ProductModel> getProductListByCategoryName(String categoryName) throws ProductValidateException;


}
