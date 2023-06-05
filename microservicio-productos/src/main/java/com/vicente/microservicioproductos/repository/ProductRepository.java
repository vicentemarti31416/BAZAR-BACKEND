package com.vicente.microservicioproductos.repository;

import com.vicente.microservicioproductos.model.Product;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Aggregation(pipeline =
            {"{ '$match': { 'hasColors': true } }, " +
            "{ '$sample': { size: ?1 } } " })
    List<Product> findProductsWithOptionalColorTrue(int numberOfElements);

    @Aggregation(pipeline =
            {"{ '$match': { 'hasColors': false } }, " +
            "{ '$sample': { size: ?1 } } " })
    List<Product> findProductsWithOptionalColorFalse(int numberOfElements);

    // @Query("{'colorCode': ?0}")
    List<Product> findByColorCode(Integer colorCode);
}
