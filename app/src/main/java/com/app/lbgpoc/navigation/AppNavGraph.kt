package com.app.lbgpoc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.lbgpoc.feature.list.presentation.ProductDetailScreen
import com.app.lbgpoc.feature.list.presentation.ProductListScreen
import com.app.lbgpoc.feature.list.presentation.ProductListViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: ProductListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.ProductList.route,
        modifier = modifier
    ) {
        composable(Screen.ProductList.route) {
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { product ->
                    viewModel.selectProduct(product)
                    navController.navigate(Screen.ProductDetail.route)
                }
            )
        }
        composable(Screen.ProductDetail.route) {
            val state by viewModel.listState.collectAsState()

            state.selectedProduct?.let { product ->
                ProductDetailScreen(
                    product = product,
                    onBackClick = {
                        viewModel.clearSelectedProduct()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

