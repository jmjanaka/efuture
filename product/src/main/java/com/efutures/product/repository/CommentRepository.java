package com.efutures.product.repository;

import com.efutures.product.entity.Comment;
import com.efutures.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByProduct(Optional<Product> product);
}
