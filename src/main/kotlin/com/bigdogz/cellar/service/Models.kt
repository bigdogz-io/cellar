package com.bigdogz.cellar.service

import org.springframework.data.mongodb.core.index.Indexed

data class Company(
        val id: String? = null,
        val name: String? = null,
        val description: String? = null,
)

data class Product(
        val id: String? = null,
        val name: String? = null,
        val productType: ProductType? = null,
        val abv: String? = null,
        val company: Company? = null
)

data class CellarItem(
        val id: String? = null,
        @Indexed
        val userId: String? = null,
        val product: Product? = null,
        val notes: String? = null
)

enum class ProductType {
    BEER, WINE, WHISKEY
}
