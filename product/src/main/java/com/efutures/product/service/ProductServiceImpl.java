package com.efutures.product.service;

import com.efutures.product.entity.Category;
import com.efutures.product.entity.Comment;
import com.efutures.product.entity.Product;
import com.efutures.product.enums.ProductStatus;
import com.efutures.product.exception.ProductValidateException;
import com.efutures.product.model.ProductModel;
import com.efutures.product.repository.CategoryRepository;
import com.efutures.product.repository.CommentRepository;
import com.efutures.product.repository.ProductRepository;
import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              CommentRepository commentRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
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
            newProduct.setCategories(fetchedProduct.get().getCategories());
            newProduct.setComments(product.getComments() == null ? new HashSet<>():product.getComments());

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
            newProduct.setComments(setComment);
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
    public Collection<ProductModel> getProductListByCategoryName(String categoryName) throws ProductValidateException, InstantiationException, IllegalAccessException, IntrospectionException {
        if (categoryName == null || categoryName.isEmpty())
            throw new ProductValidateException("product category must be entered.");

        List<Map<String, Object>> mapList = productRepository.findProductModelByCategoryName(categoryName);
        List<ProductModel> list = new ArrayList<>(mapList.size());
        for (Map<String, Object> map : mapList) {
            ProductModel productModel = new ProductModel();
            copyProperties(map, productModel);
            Optional<Product> product = productRepository.findByProductName(productModel.getProductName());
            List<Comment> commentList = commentRepository.findByProduct(product);
            productModel.setCommentList(commentList);
            list.add(productModel);
        }
        return list;

    }

    public static void copyProperties(Map<String,Object> map, Object target) throws IntrospectionException {
        if(map == null || target == null || map.isEmpty()){
            return;
        }
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = Introspector.getBeanInfo(actualEditable).getPropertyDescriptors();
        for (PropertyDescriptor targetPd : targetPds) {
            if(targetPd.getWriteMethod() == null) {
                continue;
            }
            try {
                String key = targetPd.getName();
                Object value = map.get(key);
                // Aquí se juzga si el siguiente valor está vacío
                setValue(target, targetPd, value);
            } catch (Exception ex) {
                throw new FatalBeanException("Could not copy properties from source to target", ex);
            }
        }
    }
    /**
     * Set Values
     * @param target
     * @param targetPd
     * @param value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void setValue(Object target, PropertyDescriptor targetPd, Object value) throws IllegalAccessException, InvocationTargetException {
        // Aquí se juzga si el siguiente valor está vacío
        if (value != null) {
            Method writeMethod = targetPd.getWriteMethod();
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            writeMethod.invoke(target, value);
        }
    }

}
