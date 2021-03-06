package com.bigdogz.cellar.endpoint

import com.bigdogz.cellar.service.CellarCommandHandler
import com.bigdogz.cellar.service.CellarQueryHandler
import com.bigdogz.cellar.service.ProductType
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
class CellarRestEndpoint(private var commandHandler: CellarCommandHandler, private var cellarQueryHandler: CellarQueryHandler) {

    @GetMapping("/product/{productId}")
    fun getProductById(@PathVariable("productId") productId: String): ProductView {
        return cellarQueryHandler.fetchProductById(productId)
    }

    @GetMapping("/product")
    fun getProducts(@RequestParam("productType", required = false, defaultValue = "WINE") productType: ProductType): List<ProductView> {
        return cellarQueryHandler.getProducts(productType)
    }

    @PostMapping("/product")
    fun createProduct(@RequestBody createProductCommand: CreateProduct): String {
        return commandHandler.createProduct(createProductCommand)
    }

    @PostMapping("/cellar/{userId}")
    fun createCellarItem(
        @PathVariable("userId") userId: String,
        @RequestBody createCellarItem: CreateCellarItem): String {

        return commandHandler.createCellarItem(userId, createCellarItem)
    }

    @GetMapping("/cellar/{userId}")
    fun getCellarItems(@PathVariable("userId") userId: String): List<CellarItemView> {
        return cellarQueryHandler.getCellarItems(userId)
    }
}
