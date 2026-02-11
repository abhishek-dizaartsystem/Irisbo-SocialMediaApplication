package com.example.sociamediaapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MarketplaceViewModel: ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>( emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun increaseQuantity(productId: String){
        _cartItems.update{current->
            current.map { item->
                if(item.productId == productId){
                    item.copy(productCount = item.productCount+1)
                }
                else item
            }
        }
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
    }

    fun removeFromCart(productId: String) {
        _cartItems.update { current ->
            current.filterNot { it.productId == productId }
        }
    }

}