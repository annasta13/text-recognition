package com.example.textrecognizer.ui.graph

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.textrecognizer.util.Constants

/*
* Created by Annas Surdyanto on 02/03/2023
*/

class AppAction(navController: NavHostController) {
    private val inclusiveNavOptions = NavOptions.Builder()
        .setPopUpTo(destinationId = 0, inclusive = true, saveState = true)
        .build()

    val navigate: (String) -> Unit = { destination ->
        navController.navigate(route = destination)
    }

    val popBackStackWithRefresh: () -> Unit = {
        navController.previousBackStackEntry?.savedStateHandle?.set(Constants.shouldRefresh, true)
        navController.popBackStack()
    }

    val navigateInclusively: (String) -> Unit = { destination ->
        navController.navigate(
            route = destination,
            navOptions = inclusiveNavOptions
        )
    }

    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }
}