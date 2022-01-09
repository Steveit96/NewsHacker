package com.steven.newshacker.network

typealias DataState<T> = ResultState.DataState<T>
typealias DataStateSuccess<T> = ResultState.DataState.Success<T>
typealias DataStateError = ResultState.DataState.Error
typealias State = ResultState.State
typealias StateLoading = ResultState.State.Loading
typealias StateIdle = ResultState.State.IDLE
typealias StateDone = ResultState.State.Done
typealias StateEmpty = ResultState.State.Empty
typealias StateRetrying = ResultState.State.Retrying