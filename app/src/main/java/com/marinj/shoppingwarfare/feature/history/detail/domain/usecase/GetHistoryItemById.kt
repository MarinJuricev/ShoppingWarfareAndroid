package com.marinj.shoppingwarfare.feature.history.detail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetHistoryItemById @Inject constructor(
    private val historyRepository: HistoryRepository,
) {

    suspend operator fun invoke(
        historyItemId: String
    ): Either<Failure, HistoryItem> =
        historyRepository.getHistoryItemById(historyItemId)
}
