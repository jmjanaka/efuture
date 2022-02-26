package com.efutures.product.controller;

import com.efutures.product.entity.Comment;
import com.efutures.product.entity.Product;
import com.efutures.product.exception.ProductValidateException;
import com.efutures.product.model.ProductModel;
import com.efutures.product.model.ResponseModel;
import com.efutures.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {

        try {
            productService.createProduct(product);
            return new ResponseEntity<Object>(new ResponseModel("success", "Successfully created the new product", null), HttpStatus.OK);

        } catch (ProductValidateException productValidateException){
            return new ResponseEntity<Object>(new ResponseModel("validate-failure", productValidateException.getMessage(), productValidateException.getMessage()), HttpStatus.OK);

        }catch (Exception ex) {
            return new ResponseEntity<Object>(new ResponseModel("error", "Error occurred when product creating", ex.getMessage()), HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product) {

        try {
            productService.updateProduct(product);
            return new ResponseEntity<Object>(new ResponseModel("success", "Successfully updated the product details", null), HttpStatus.OK);

        } catch (ProductValidateException productValidateException){
            return new ResponseEntity<Object>(new ResponseModel("validate-failure", productValidateException.getMessage(), productValidateException.getMessage()), HttpStatus.CONFLICT);

        }catch (Exception ex) {
            return new ResponseEntity<Object>(new ResponseModel("error", "Error occurred when product updating", ex.getMessage()), HttpStatus.OK);
        }
    }

    @PutMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId,
                                           @RequestParam String comment) throws Exception {

        try {
            productService.deleteProduct(productId, comment);
            return new ResponseEntity<Object>(new ResponseModel("success", "Successfully deleted the product", null), HttpStatus.OK);

        }catch (ProductValidateException productValidateException){
            return new ResponseEntity<Object>(new ResponseModel("validate-failure", "Error occurred when deleting an item",
                    productValidateException.getMessage()), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(new ResponseModel("error", "Error occurred when product deleting", ex.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/productList")
    public ResponseEntity<?> getProductListByCategoryName(@RequestParam String categoryName) {

        try {
            List<ProductModel> products = productService.getProductListByCategoryName(categoryName);
            return new ResponseEntity<Object>(new ResponseModel("success", "Successfully got the product list", products), HttpStatus.OK);

        } catch (ProductValidateException productValidateException){
            return new ResponseEntity<Object>(new ResponseModel("validate-failure", productValidateException.getMessage(), productValidateException.getMessage()), HttpStatus.OK);

        }catch (Exception ex) {
            return new ResponseEntity<Object>(new ResponseModel("error", "Error occurred when product getting", ex.getMessage()), HttpStatus.OK);
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<Object>(new ResponseModel("validate-failure", "Error occurred when validating data", errors), HttpStatus.UNPROCESSABLE_ENTITY);

    }
}
