package com.bigdogz.cellar.service

import com.bigdogz.cellar.endpoint.CellarItemView
import com.bigdogz.cellar.endpoint.CompanyView
import com.bigdogz.cellar.endpoint.ProductView
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class CellarQueryHandler(val productRepository: ProductRepository, val cellarItemRepository: CellarItemRepository) {
    fun fetchProductById(productId: String): ProductView {
        val product = productRepository.findById(productId).orElseThrow()
        return ProductView(product.id, product.name, product.productType, product.abv, CompanyView(null, product.company?.name, product.company?.description))

    }

    fun getProducts(productType: ProductType): List<ProductView> {
        return productRepository.findByProductType(productType)
    }

    fun getCellarItems(userId: String): List<CellarItemView> {
        return cellarItemRepository.findByUserId(userId)
    }
}

