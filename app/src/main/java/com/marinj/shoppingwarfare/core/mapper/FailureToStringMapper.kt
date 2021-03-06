package com.marinj.shoppingwarfare.core.mapper

import com.marinj.shoppingwarfare.core.result.Failure
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FailureToStringMapper @Inject constructor() {

    fun map(origin: Failure): String {
        return when (origin) {
            is Failure.ErrorMessage -> origin.errorMessage
            Failure.Unknown -> "Unknown Error Occurred, please try again later"
        }
    }
}
