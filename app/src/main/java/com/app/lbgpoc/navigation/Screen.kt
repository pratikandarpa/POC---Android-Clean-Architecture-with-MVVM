package com.app.lbgpoc.navigation

sealed class Screen(val route: String) {
    object ProductList : Screen("products_list")
    object ProductDetail : Screen("product_detail")
}
