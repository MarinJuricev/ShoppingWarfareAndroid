package com.marinj.shoppingwarfare.feature.cart.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val CART_ITEM_NAME = "cartItemName"

@ExperimentalTime
@ExperimentalCoroutinesApi
class CartRepositoryImplTest {

    private val cartDao: CartDao = mockk()
    private val localToDomainCartItemMapper: Mapper<CartItem, LocalCartItem> = mockk()
    private val domainToLocalCartItemMapper: Mapper<LocalCartItem, CartItem> = mockk()

    private lateinit var sut: CartRepository

    @Before
    fun setUp() {
        sut = CartRepositoryImpl(
            cartDao,
            localToDomainCartItemMapper,
            domainToLocalCartItemMapper,
        )
    }

    @Test
    fun `observeCartItems should return cartItems`() = runBlockingTest {
        val cartItem = mockk<CartItem>()
        val cartItemList = listOf(cartItem)
        val localCartItem = mockk<LocalCartItem>()
        val localCartItemList = listOf(localCartItem)
        coEvery {
            localToDomainCartItemMapper.map(localCartItem)
        } coAnswers { cartItem }
        coEvery {
            cartDao.observeCartItems()
        } coAnswers { flow { emit(localCartItemList) } }

        sut.observeCartItems().test {
            assertThat(awaitItem()).isEqualTo(cartItemList)
            awaitComplete()
        }
    }

    @Test
    fun `upsertCartItem should return LeftFailure when cartDao returns 0L`() = runBlockingTest {
        val cartItem = mockk<CartItem>()
        val localCartItem = mockk<LocalCartItem>().apply {
            every { name } returns CART_ITEM_NAME
        }
        val daoResult = 0L
        coEvery {
            domainToLocalCartItemMapper.map(cartItem)
        } coAnswers { localCartItem }
        coEvery {
            cartDao.upsertCartItem(localCartItem)
        } coAnswers { daoResult }

        val actualResult = sut.upsertCartItem(cartItem)
        val expectedResult = Failure.ErrorMessage("Error while adding $CART_ITEM_NAME").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `upsertCartItem should return RightUnit when cartDao returns everything but 0L`() =
        runBlockingTest {
            val cartItem = mockk<CartItem>()
            val localCartItem = mockk<LocalCartItem>()
            val daoResult = 1L
            coEvery {
                domainToLocalCartItemMapper.map(cartItem)
            } coAnswers { localCartItem }
            coEvery {
                cartDao.upsertCartItem(localCartItem)
            } coAnswers { daoResult }

            val actualResult = sut.upsertCartItem(cartItem)
            val expectedResult = Unit.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `deleteCartItemById should return RightUnit`() =
        runBlockingTest {
            val cartItemId = "1"
            coEvery {
                cartDao.deleteCartItemById(cartItemId)
            } coAnswers { Unit }

            val actualResult = sut.deleteCartItemById(cartItemId)
            val expectedResult = Unit.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}
