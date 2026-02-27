package com.example.sociamediaapplication.view.navigation

sealed class MarketRoutes(val route: String) {
    object Marketplace: MarketRoutes("marketplace")
    object Wishlist: MarketRoutes("wishlist")
    object Cart: MarketRoutes("cart")
    object Product: MarketRoutes("product/{productId}"){
        fun createRoute(productId: String) = "product/$productId"
    }

    object AddProduct: MarketRoutes("addProduct")

//    object EditProduct: MarketRoutes("editProduct/{productId}"){
//        fun createRoute(productId: String) = "editProduct/$productId"
//    }

    object CheckOut: MarketRoutes("checkOut")
}