package com.marinj.shoppingwarfare.feature.history.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.feature.history.domain.usecase.FilterHistoryItems
import com.marinj.shoppingwarfare.feature.history.domain.usecase.ObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.mapper.HistoryItemToUiHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnGetHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnHistoryItemClick
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnSearchTriggered
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnSearchUpdated
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewEffect
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewState
import com.marinj.shoppingwarfare.feature.history.presentation.model.UiHistoryItem
import com.marinj.shoppingwarfare.feature.historydetail.presentation.navigation.HistoryDetailNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val observeHistoryItems: ObserveHistoryItems,
    private val historyItemToUiHistoryItemMapper: HistoryItemToUiHistoryItemMapper,
    private val filterHistoryItems: FilterHistoryItems,
    private val navigator: Navigator,
) : BaseViewModel<HistoryEvent>() {

    private val _viewState = MutableStateFlow(HistoryViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<HistoryViewEffect>()
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: HistoryEvent) {
        when (event) {
            OnGetHistoryItems -> handleGetHistoryItems()
            OnSearchTriggered -> handleSearchTriggered()
            is OnSearchUpdated -> handleSearchUpdated(event.newSearch)
            is OnHistoryItemClick -> handleHistoryItemClick(event.uiHistoryItem)
        }
    }

    private fun handleGetHistoryItems() = viewModelScope.launch {
        observeHistoryItems()
            .onStart { updateIsLoading(isLoading = true) }
            .catch { handleGetHistoryItemsError() }
            .map { historyItems ->
                historyItems.map { historyItem -> historyItemToUiHistoryItemMapper.map(historyItem) }
            }
            .collect { historyItems ->
                _viewState.safeUpdate(
                    _viewState.value.copy(
                        historyItems = historyItems,
                        nonFilteredHistoryItems = historyItems,
                        isLoading = false,
                    )
                )
            }
    }

    private fun handleSearchTriggered() {
        _viewState.safeUpdate(
            _viewState.value.copy(
                historyItems = filterHistoryItems(
                    listToFilter = _viewState.value.nonFilteredHistoryItems,
                    searchQuery = _viewState.value.searchText,
                )
            )
        )
    }

    private fun handleSearchUpdated(newSearch: String) = viewModelScope.launch {
        _viewState.safeUpdate(
            _viewState.value.copy(searchText = newSearch)
        )
        handleSearchTriggered()
    }

    private suspend fun handleGetHistoryItemsError() {
        updateIsLoading(false)
        _viewEffect.send(Error("Failed to history items, please try again later."))
    }

    private fun handleHistoryItemClick(uiHistoryItem: UiHistoryItem) = viewModelScope.launch {
        navigator.emitDestination(
            Destination(
                HistoryDetailNavigation.createHistoryDetailRoute(
                    historyItemId = uiHistoryItem.id,
                    historyName = uiHistoryItem.cartName,
                )
            )
        )
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _viewState.safeUpdate(
            _viewState.value.copy(
                isLoading = isLoading
            )
        )
    }
}
