package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareIconButton
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent

@Composable
fun QuantityPicker(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    onCartEvent: (CartEvent) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShoppingWarfareIconButton(
            buttonSize = 24.dp,
            onClick = {
                onCartEvent(CartEvent.CartItemQuantityChanged(cartItem, cartItem.quantity.dec()))
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(id = R.drawable.minus_icon),
                tint = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White,
                contentDescription = stringResource(R.string.decrease_quantity)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = cartItem.quantity.toString(), // TODO Introduce a UI model so that we don't have to do toString in the UI
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.width(8.dp))
        ShoppingWarfareIconButton(
            buttonSize = 24.dp,
            onClick = {
                onCartEvent(CartEvent.CartItemQuantityChanged(cartItem, cartItem.quantity.inc()))
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(id = R.drawable.add_icon),
                tint = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White,
                contentDescription = stringResource(R.string.increase_quantity)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}