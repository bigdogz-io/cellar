package com.bigdogz.cellar.endpoint

import com.bigdogz.cellar.service.ProductType

data class ProductView(
        val id: String? = null,
        val name: String? = null,
        val type: ProductType? = null,
        val abv: String? = null,
        val company: CompanyView? = null
)

data class CompanyView(
        val id: String? = null,
        val name: String? = null,
        val description: String? = null,
)

data class CellarItemView(
        val id: String? = null,
        val userId: String? = null,
        val product: ProductView? = null,
        val notes: String? = null
)

class CreateProduct(
        val name: String,
        val type: ProductType,
        val abv: String? = null,
        val company: CompanyView
)

data class CreateCellarItem(
        val userId: String? = null,
        val productId: String? = null,
        val notes: String? = null
)

