package com.app.lbgpoc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.lbgpoc.feature.list.presentation.ui.ProductDetailScreen
import com.app.lbgpoc.feature.list.presentation.ui.ProductListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ProductList.route,
        modifier = modifier
    ) {
        composable(Screen.ProductList.route) {
            ProductListScreen(
                navController = navController,
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.route)
                }
            )
        }
        composable(Screen.ProductDetail.route) {
            ProductDetailScreen(
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

