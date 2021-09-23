package com.marinj.shoppingwarfare.core.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class TopBarViewState(
    @StringRes val title: Int? = null,
    @StringRes val subTitle: Int? = null,
    val icon: (@Composable () -> Unit)? = null,
    val onActionClick: (() -> Unit)? = null,
)
