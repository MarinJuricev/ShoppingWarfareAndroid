package com.marinj.shoppingwarfare.feature.history.list.data.mapper

import com.marinj.shoppingwarfare.feature.history.list.data.model.LocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import javax.inject.Inject

class DomainToLocalHistoryItemMapper @Inject constructor() {

    fun map(origin: HistoryItem): LocalHistoryItem {
        return with(origin) {
            LocalHistoryItem(
                historyItemId = id,
                receiptPath = receiptPath,
                cartName = cartName,
                timestamp = timestamp,
                historyCartItems = historyCartItems,
            )
        }
    }
}
