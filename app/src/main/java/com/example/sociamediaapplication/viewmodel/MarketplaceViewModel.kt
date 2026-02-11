package com.example.sociamediaapplication.viewmodel

import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.model.CartItem
import com.example.sociamediaapplication.model.WishlistItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update

class MarketplaceViewModel: ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>( emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _wishListItems = MutableStateFlow<List<WishlistItem>>(emptyList())
    val wishListItems: StateFlow<List<WishlistItem>> = _wishListItems

    private val _cartSum = MutableStateFlow<Double>(0.0)
    val cartSum: StateFlow<Double> = _cartSum

    fun addToWishlist(item: WishlistItem){
        _wishListItems.update { current->
            current + item
        }
    }

    fun removeFromWishlist(productId: String){
        _wishListItems.update { current->
            current.filterNot { it.productId == productId }
        }
    }

    fun increaseQuantity(productId: String){
        _cartItems.update{current->
            current.map { item->
                if(item.productId == productId){
                    item.copy(productCount = item.productCount+1)
                }
                else item
            }
        }
        totalPrice()
    }

    fun decreaseQuantity(productId: String) {
        _cartItems.update { current ->
            current.mapNotNull { item ->
                if (item.productId == productId) {

                    if (item.productCount > 1) {
                        item.copy(productCount = item.productCount - 1)
                    } else {
                        null  // remove item when quantity hits 0
                    }

                } else {
                    item
                }
            }
        }
        totalPrice()
    }

    fun addToCart(
        item: CartItem
    ){
        _cartItems.update { current->
            val existing = current.find { it.productId == item.productId }

            if (existing != null) {
                current.map {
                    if (it.productId == item.productId) {
                        it.copy(productCount = it.productCount + 1)
                    } else it
                }
            } else {
                current + item
            }
        }
        totalPrice()
    }

    fun removeFromCart(productId: String) {
        _cartItems.update { current ->
            current.filterNot { it.productId == productId }
        }
        totalPrice()
    }

    fun totalPrice() {
        val sum = _cartItems.value.sumOf { item ->
            item.price.toDouble() * item.productCount
        }

        _cartSum.value = sum
    }

}