package com.bigdogz.cellar

import com.bigdogz.cellar.endpoint.CompanyView
import com.bigdogz.cellar.endpoint.CreateCellarItem
import com.bigdogz.cellar.endpoint.CreateProduct
import com.bigdogz.cellar.endpoint.ProductView
import com.bigdogz.cellar.service.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CellarApplicationTests(@Autowired val restTemplate: TestRestTemplate,
                             @Autowired val productRepository: ProductRepository,
                             @Autowired val cellarItemRepository: CellarItemRepository) {

    @Test
    fun `Assert product returned with valid productId`() {
        val testProduct = productRepository.save(Product("1232132", "Big Beer", ProductType.BEER, "12.5%", Company("322423", "Big Company", "Beer company")))
        val entity = restTemplate.getForEntity<ProductView>("/product/{productId}", mapOf("productId" to testProduct.id))
        assertNotNull(entity)
        assertEquals(entity.statusCode, HttpStatus.OK)
    }

    @Test
    fun `Assert product list returned without a product type selected`() {
        val testProduct = productRepository.save(Product("1232132", "Big Beer", ProductType.BEER, "12.5%", Company("322423", "Big Company", "Beer company")))
        val testProduct1 = productRepository.save(Product("1232132", "Good Wine", ProductType.WINE, "11.2%", Company("3423423423423", "Big Winery", "Wine company")))

        val entity = restTemplate.getForEntity<List<ProductView>>("/product", "")
        assertNotNull(entity)
        assertEquals(entity.statusCode, HttpStatus.OK)
        val productViewList = entity.body
        assertNotNull(productViewList)
        assertEquals(1, productViewList?.size)
    }

    @Test
    fun `Assert cellar items returned for valid user`() {
        val userId = UUID.randomUUID().toString()
        val testItem = cellarItemRepository.save(CellarItem(
                UUID.randomUUID().toString(),
                userId,
                Product(UUID.randomUUID().toString(), "Big Beer", ProductType.BEER, "12.5%", Company("322423", "Big Company", "Beer company")), ""))

        val testItem1 = cellarItemRepository.save(CellarItem(
                UUID.randomUUID().toString(),
                userId,
                Product(UUID.randomUUID().toString(), "Big Beer", ProductType.WINE, "11.5%", Company("322427", "Wine Company", "Wine company")), ""))

        val entity = restTemplate.getForEntity<List<ProductView>>("/cellar/{userId}", mapOf("userId" to userId))
        assertNotNull(entity)
        assertEquals(entity.statusCode, HttpStatus.OK)
        val productViewList = entity.body
        assertNotNull(productViewList)
        assertEquals(2, productViewList?.size)
    }

    @Test
    fun `Assert create new product successfully`() {
        val createProductCommand = CreateProduct("Big Beer", ProductType.BEER, "12.5%", CompanyView("123", "Big Company", "Beer company"))

        val entity = restTemplate.postForEntity<String>("/product", createProductCommand)
        assertNotNull(entity)
        assertEquals(entity.statusCode, HttpStatus.OK)
        val productId = entity.body
        assertNotNull(productId)
    }

    @Test
    fun `Assert create new cellarItem successfully`() {
        val productId = UUID.randomUUID().toString()
        val testProduct = productRepository.save(Product(productId, "Big Beer", ProductType.BEER, "12.5%", Company("322423", "Big Company", "Beer company")))
        val userId = UUID.randomUUID().toString()
        val createProductCommand = CreateCellarItem(productId, null)

        val entity = restTemplate.postForEntity<String>("/cellar/{userId}", createProductCommand, mapOf("userId" to userId))
        assertNotNull(entity)
        assertEquals(entity.statusCode, HttpStatus.OK)
        val cellarItemId = entity.body
        assertNotNull(cellarItemId)
    }
}
