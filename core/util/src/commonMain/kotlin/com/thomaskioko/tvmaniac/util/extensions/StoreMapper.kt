package com.thomaskioko.tvmaniac.util.extensions

import com.thomaskioko.tvmaniac.util.model.DefaultError
import com.thomaskioko.tvmaniac.util.model.Either
import com.thomaskioko.tvmaniac.util.model.Failure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import org.mobilenativefoundation.store.store5.StoreReadResponse

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<StoreReadResponse<T>>.mapResult(): Flow<Either<Failure, T>> =
    distinctUntilChanged()
        .flatMapLatest { result ->
            when (val data = result.dataOrNull()) {
                null -> flowOf(Either.Left(DefaultError(result.errorMessageOrNull())))
                else -> flowOf(Either.Right(data))
            }
        }