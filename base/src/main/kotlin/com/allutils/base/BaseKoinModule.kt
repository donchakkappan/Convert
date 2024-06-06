package com.allutils.base

import com.allutils.base.nav.NavManager
import org.koin.dsl.module

val baseModule = module {

    single { NavManager() }
}
