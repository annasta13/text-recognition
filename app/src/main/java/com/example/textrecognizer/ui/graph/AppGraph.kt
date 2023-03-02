package com.example.textrecognizer.ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.textrecognizer.ui.screen.collection.CollectionScreen
import com.example.textrecognizer.ui.screen.edit.EditScreen
import com.example.textrecognizer.ui.screen.main.MainScreen
import com.example.textrecognizer.util.Constants

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@Composable
fun AppGraph() {
    val navController: NavHostController = rememberNavController()
    val action = remember(navController) { AppAction(navController = navController) }

    NavHost(navController, startDestination = AppDestination.MAIN_SCREEN) {
        composable(route = AppDestination.MAIN_SCREEN) {
            MainScreen(onNavigate = action.navigate)
        }

        composable(route = AppDestination.COLLECTION_SCREEN) {
            val savedState = it.savedStateHandle
            CollectionScreen(
                onNavigate = action.navigate,
                onNavigateUp = action.popBackStack,
                savedState = savedState
            )
        }

        composable(
            route = "${AppDestination.EDIT_SCREEN}/{${Constants.param}}/{${Constants.gate}}",
            arguments = listOf(
                navArgument(Constants.param) { type = NavType.StringType },
                navArgument(Constants.gate) { type = NavType.StringType }
            )
        ) {
            val documentId = it.arguments?.getString(Constants.param) ?: ""
            val gate = it.arguments?.getString(Constants.gate) ?: ""
            EditScreen(
                onNavigateUp = action.popBackStack,
                documentId = documentId,
                gate = gate,
                onUpdated = action.popBackStackWithRefresh
            )
        }
    }
}