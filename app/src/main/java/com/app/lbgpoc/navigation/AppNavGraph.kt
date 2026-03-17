package com.app.lbgpoc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.lbgpoc.feature.list.presentation.ProductDetailScreen
import com.app.lbgpoc.feature.list.presentation.ProductListScreen
import com.app.lbgpoc.feature.list.presentation.ProductListViewModel
import com.app.lbgpoc.feature.list.domain.model.Product
import com.google.gson.Gson

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val gson = Gson()

    NavHost(
        navController = navController,
        startDestination = Screen.ProductList.route,
        modifier = modifier
    ) {
        composable(Screen.ProductList.route) {
            val viewModel: ProductListViewModel = hiltViewModel()
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { product ->
                    val productJson = gson.toJson(product)
                    val encodedJson = android.net.Uri.encode(productJson)
                    navController.navigate(Screen.ProductDetail.createRoute(encodedJson))
                }
            )
        }
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
            val product = try {
                productJson?.let { gson.fromJson(it, Product::class.java) }
            } catch (e: Exception) {
                null
            }

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

