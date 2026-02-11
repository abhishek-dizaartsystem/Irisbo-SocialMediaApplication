package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.view.screens.CartScreen
import com.example.sociamediaapplication.view.screens.MarketplaceScreen
import com.example.sociamediaapplication.view.screens.ProductScreen
import com.example.sociamediaapplication.view.screens.WishlistScreen
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

@Composable
fun MarketNavGraph(
    bNavController: NavController = rememberNavController()
){
    val navController = rememberNavController()

    val viewModel: MarketplaceViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MarketRoutes.Marketplace.route
    ){
        composable(MarketRoutes.Marketplace.route) {
            MarketplaceScreen(
                bNavController = bNavController,
                navController = navController,
                onProductClick = {productId->
                    navController.navigate(
                        MarketRoutes.Product.createRoute(productId)
                    )
                },
                viewModel = viewModel
            )
        }
        composable(MarketRoutes.Cart.route) {
            CartScreen(
                navController = navController,
                onCheckout = {
                    navController.navigate(MarketRoutes.CheckOut.route)
                },
                viewModel = viewModel
            )
        }
        composable(MarketRoutes.Wishlist.route) {
            WishlistScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = MarketRoutes.Product.route,
            arguments = listOf(
                navArgument("productId"){type = NavType.StringType}
            )) {backStackEntry->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductScreen(
                navController = navController,
                productId = productId?:"",
                viewModel = viewModel
            )
        }
    }
}