package com.efutures.product.service;

import com.efutures.product.entity.Category;
import com.efutures.product.exception.ProductValidateException;
import com.efutures.product.entity.Product;
import com.efutures.product.model.ProductModel;

import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.util.Collection;
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
     * @param productId primary key of the {@link Product} table
     * @return
     */
    public void deleteProduct(Long productId, String comment) throws ProductValidateException;

    /**
     *
     * @param categoryName The product category name
     * @return
     * @throws ProductValidateException
     */
    public Collection<ProductModel> getProductListByCategoryName(String categoryName) throws ProductValidateException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     *
     * @param price the premium price
     * @return
     * @throws ProductValidateException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Collection<ProductModel> getProductListByPrice(BigDecimal price) throws ProductValidateException, IntrospectionException, InstantiationException, IllegalAccessException;


}
