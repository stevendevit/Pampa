package com.stevendevit.pampa.module

import org.koin.core.module.Module

/**
 * Created by stevendevit on 13/01/2020.
 * tankadeveloper@gmail.com
 */
interface IKModuleProvider {

    val modules: List<Module>
}