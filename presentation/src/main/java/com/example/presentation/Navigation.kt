package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.navigation.Screen
import com.example.presentation.productDetails.ProductDetailScreen
import com.example.presentation.productDetails.ProductDetailViewModel
import com.example.presentation.products.ProductListViewModel
import com.example.presentation.products.ProductsListScreen

/**
 * This composable function defines the navigation graph for the application.
 *
 * It utilizes Jetpack Navigation and Hilt to manage navigation flow and inject ViewModels.
 */
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ProductListScreen.route,
    ) {
        composable(route = Screen.ProductListScreen.route) {
            val viewModel: ProductListViewModel = hiltViewModel()
            ProductsListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "${Screen.ProductDetailsScreen.route}/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->
            val detailViewModel: ProductDetailViewModel = hiltViewModel()
            ProductDetailScreen(
                navBackStackEntry.arguments!!.getInt("productId"),
                viewModel = detailViewModel,
            )
        }
    }
}
