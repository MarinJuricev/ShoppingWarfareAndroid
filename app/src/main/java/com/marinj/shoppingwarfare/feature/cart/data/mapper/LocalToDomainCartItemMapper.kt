package com.marinj.shoppingwarfare.feature.cart.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import javax.inject.Inject

class LocalToDomainCartItemMapper @Inject constructor() : Mapper<CartItem, LocalCartItem> {

    override suspend fun map(origin: LocalCartItem): CartItem {
        return with(origin) {
            CartItem(
                cartItemId,
                name,
                quantity
            )
        }
    }
}
