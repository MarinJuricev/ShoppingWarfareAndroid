package com.marinj.shoppingwarfare.feature.cart.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"
private const val QUANTITY = 1

@ExperimentalCoroutinesApi
class LocalToDomainCartItemMapperTest {

    private lateinit var sut: Mapper<CartItem, LocalCartItem>

    @Before
    fun setUp() {
        sut = LocalToDomainCartItemMapper()
    }

    @Test
    fun `map should map id`() = runBlockingTest {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { cartItemId } returns ID
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map name`() = runBlockingTest {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { name } returns NAME
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.name).isEqualTo(NAME)
    }

    @Test
    fun `map should map quantity`() = runBlockingTest {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { quantity } returns QUANTITY
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.quantity).isEqualTo(QUANTITY)
    }
}