package com.lj.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lj.app.composable.HelloWorldComposable

private const val HOME_DESTINATION = "home"

/**
 * Define the navigation through the application.
 */
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_DESTINATION) {
        composable(HOME_DESTINATION)
        {
            HelloWorldComposable()
        }
    }
}
