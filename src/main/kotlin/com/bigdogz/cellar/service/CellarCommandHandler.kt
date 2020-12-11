package com.bigdogz.cellar.service

import com.bigdogz.cellar.endpoint.CreateCellarItem
import com.bigdogz.cellar.endpoint.CreateProduct
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class CellarCommandHandler(val productRepository: ProductRepository, val cellarItemRepository: CellarItemRepository) {
    fun createProduct(createProductCommand: CreateProduct): String {
        val product = productRepository.save(Product(
                null,
                createProductCommand.name,
                createProductCommand.type,
                createProductCommand.abv,
                Company(null,
                        createProductCommand.company.name,
                        createProductCommand.company.description
                )))
        return product.id!!
    }

    fun createCellarItem(userId: String, createCellarItem: CreateCellarItem): String {
        if (createCellarItem.productId == null) {
            throw RuntimeException("productId cannot be null")
        }

        val product = productRepository.findById(createCellarItem.productId)

        val cellarItem = cellarItemRepository.save(CellarItem(
                null,
                userId,
                product.orElseThrow(),
                createCellarItem.notes

        ))
        return cellarItem.id!!
    }
}
