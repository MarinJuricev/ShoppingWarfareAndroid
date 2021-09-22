package com.marinj.shoppingwarfare.feature.categorydetail.presentation.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.DEFAULT_QUANTITY
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import javax.inject.Inject

class ProductToCartItemMapper @Inject constructor() : Mapper<CartItem, Product> {

    override suspend fun map(origin: Product): CartItem {
        return with(origin) {
            CartItem(
                id = id,
                name = name,
                quantity = DEFAULT_QUANTITY
            )
        }
    }
}