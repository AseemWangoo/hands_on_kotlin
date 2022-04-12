package com.aseemwangoo.handsonkotlin.navigation

import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class DestinationsNavigatorImpl : DestinationsNavigator {

    override fun clearBackStack(route: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun navigate(
        route: String,
        onlyIfResumed: Boolean,
        builder: NavOptionsBuilder.() -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun navigateUp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun popBackStack(): Boolean {
        TODO("Not yet implemented")
    }

    override fun popBackStack(route: String, inclusive: Boolean, saveState: Boolean): Boolean {
        TODO("Not yet implemented")
    }
}
