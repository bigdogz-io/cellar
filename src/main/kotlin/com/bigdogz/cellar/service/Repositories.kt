package com.bigdogz.cellar.service

import com.bigdogz.cellar.endpoint.CellarItemView
import com.bigdogz.cellar.endpoint.ProductView
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, String> {
    fun findByProductType(productType: ProductType): List<ProductView>
}

interface CellarItemRepository : MongoRepository<CellarItem, String> {
    fun findByUserId(userId: String): List<CellarItemView>
}
