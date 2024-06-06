package com.allutils.base.presentation.viewmodel

interface BaseAction<State> {
    fun reduce(state: State): State
}
