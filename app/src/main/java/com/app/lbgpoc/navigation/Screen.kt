package com.app.lbgpoc.navigation

sealed class Screen(val route: String) {
    object ProductList : Screen("products_list")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}
