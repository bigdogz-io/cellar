package com.bigdogz.cellar.service

import com.bigdogz.cellar.endpoint.CreateCellarItemCommand
import com.bigdogz.cellar.endpoint.CreateProductCommand
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class CellarCommandHandler(val productRepository: ProductRepository, val cellarItemRepository: CellarItemRepository) {
    fun createProduct(createProductCommand: CreateProductCommand): String {
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

    fun createCellarItem(createCellarItemCommand: CreateCellarItemCommand): String {
        if (createCellarItemCommand.productId == null) {
            throw RuntimeException("productId cannot be null")
        }

        val product = productRepository.findById(createCellarItemCommand.productId)

        val cellarItem = cellarItemRepository.save(CellarItem(
                null,
                createCellarItemCommand.userId,
                product.orElseThrow(),
                createCellarItemCommand.notes

        ))
        return cellarItem.id!!
    }
}
