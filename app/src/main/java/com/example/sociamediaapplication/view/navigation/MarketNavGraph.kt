package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.MarketplaceRepository
import com.example.sociamediaapplication.view.screens.AddProductScreen
import com.example.sociamediaapplication.view.screens.CartScreen
import com.example.sociamediaapplication.view.screens.MarketplaceScreen
import com.example.sociamediaapplication.view.screens.ProductScreen
import com.example.sociamediaapplication.view.screens.WishlistScreen
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel
import com.example.sociamediaapplication.viewmodel.factory.MarketplaceViewModelFactory

@Composable
fun MarketNavGraph(
    bNavController: NavController = rememberNavController()
){
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }
    val repository = remember { MarketplaceRepository(tokenManager) }
    val factory = remember { MarketplaceViewModelFactory(repository) }

    val viewModel: MarketplaceViewModel = viewModel(factory = factory)


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
            LaunchedEffect(Unit) {
                viewModel.loadCart()
            }
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
            )
        ) {backStackEntry->

            val productId = backStackEntry.arguments?.getString("productId")

            ProductScreen(
                navController = navController,
                productId = productId?:"",
                viewModel = viewModel
            )
        }

        composable(route = MarketRoutes.AddProduct.route) {
            AddProductScreen(
                viewModel = viewModel,
                navBack = { navController.popBackStack() }
            )

        }
    }
}