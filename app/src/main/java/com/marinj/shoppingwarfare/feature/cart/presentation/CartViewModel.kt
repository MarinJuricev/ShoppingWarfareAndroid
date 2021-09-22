package com.marinj.shoppingwarfare.feature.cart.presentation

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val observeCartItems: ObserveCartItems
) : BaseViewModel<CartEvent>() {

    private val _viewState = MutableStateFlow(CartViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<CartEffect>()
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: CartEvent) {
        when (event) {
            CartEvent.OnGetCartItems -> handleGetCartItems()
        }
    }

    private fun handleGetCartItems() = viewModelScope.launch {
        updateIsLoading(isLoading = true)
        observeCartItems()
            .catch { handleGetCartItemsError() }
            .collect { cartItems ->
                _viewState.safeUpdate(
                    _viewState.value.copy(
                        cartItems = cartItems,
                        isLoading = false,
                    )
                )
            }
    }

    private suspend fun handleGetCartItemsError() {
        updateIsLoading(false)
        _viewEffect.send(CartEffect.Error("Failed to fetch cart items, please try again later."))
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _viewState.safeUpdate(
            _viewState.value.copy(
                isLoading = isLoading
            )
        )
    }
}