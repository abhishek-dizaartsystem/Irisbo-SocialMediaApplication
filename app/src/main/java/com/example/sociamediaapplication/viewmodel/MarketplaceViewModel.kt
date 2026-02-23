package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.MarketplaceRepository
import com.example.sociamediaapplication.data.utils.fileToMultipart
import com.example.sociamediaapplication.data.utils.getMimeType
import com.example.sociamediaapplication.data.utils.toPart
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.CartItem
import com.example.sociamediaapplication.model.Specification
import com.example.sociamediaapplication.model.WishlistItem
import com.example.sociamediaapplication.model.response.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

class MarketplaceViewModel(
    private val repository: MarketplaceRepository
) : ViewModel() {

    // -------------------------------
    // PRODUCTS FROM API
    // -------------------------------
    private val _vendorProducts = MutableStateFlow<List<ProductResponse>>(emptyList())
    val vendorProducts: StateFlow<List<ProductResponse>> = _vendorProducts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadProducts() {
        Log.d("API_DEBUG", "loadProducts() called")

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {

                Log.d("API_DEBUG", "Calling repository.getProducts()")

                val products = repository.getProducts()

                Log.d("API_DEBUG", "Raw product list size = ${products.size}")

                products.forEachIndexed { index, product ->
                    Log.d(
                        "API_DEBUG",
                        """
                    Product[$index]:
                    id = ${product.id}
                    name = ${product.name}
                    image = ${product.product_image}
                    price = ${product.price}
                    stock = ${product.stock}
                    user = ${product.username}
                    """.trimIndent()
                    )
                }

                _vendorProducts.value = products

                Log.d("API_DEBUG", "StateFlow updated with products")

            } catch (e: Exception) {

                Log.e("API_DEBUG", "API FAILED: ${e.message}", e)

                _error.value = e.message ?: "Failed to load products"

            } finally {

                _isLoading.value = false
                Log.d("API_DEBUG", "Loading finished")

            }
        }
    }

    // -------------------------------
    // CART
    // -------------------------------
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _cartSum = MutableStateFlow<Double>(0.0)
    val cartSum: StateFlow<Double> = _cartSum

    fun addToCart(item: CartItem) {
        _cartItems.update { current ->
            val existing = current.find { it.productId == item.productId }
            if (existing != null) {
                current.map {
                    if (it.productId == item.productId)
                        it.copy(productCount = it.productCount + 1)
                    else it
                }
            } else current + item
        }
        totalPrice()
    }

    fun increaseQuantity(productId: String) {
        _cartItems.update { current ->
            current.map {
                if (it.productId == productId)
                    it.copy(productCount = it.productCount + 1)
                else it
            }
        }
        totalPrice()
    }

    fun decreaseQuantity(productId: String) {
        _cartItems.update { current ->
            current.mapNotNull {
                if (it.productId == productId) {
                    if (it.productCount > 1)
                        it.copy(productCount = it.productCount - 1)
                    else null
                } else it
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

    private fun totalPrice() {
        _cartSum.value = _cartItems.value.sumOf {
            it.price.toDouble() * it.productCount
        }
    }

    // -------------------------------
    // WISHLIST
    // -------------------------------
    private val _wishListItems = MutableStateFlow<List<WishlistItem>>(emptyList())
    val wishListItems: StateFlow<List<WishlistItem>> = _wishListItems

    fun addToWishlist(item: WishlistItem) {
        _wishListItems.update { current -> current + item }
    }

    fun removeFromWishlist(productId: String) {
        _wishListItems.update { current ->
            current.filterNot { it.productId == productId }
        }
    }

    fun addProduct(
        context: Context,
        title: String,
        category: String,
        price: String,
        stock: String,
        description: String,
        specs: List<Specification>,
        images: List<Uri>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val parts = images.map { uri ->

                    val file = uriToFile(uri, context)
                    val mime = getMimeType(context, uri)

                    fileToMultipart("product_image", file, mime)
                }

                Log.d("UPLOAD_DEBUG", "TITLE = $title")
                Log.d("UPLOAD_DEBUG", "CATEGORY = $category")
                Log.d("UPLOAD_DEBUG", "PRICE = $price")
                Log.d("UPLOAD_DEBUG", "STOCK = $stock")
                Log.d("UPLOAD_DEBUG", "DESC = $description")
                Log.d("UPLOAD_DEBUG", "IMAGES COUNT = ${images.size}")

                parts.forEach {
                    Log.d("UPLOAD_DEBUG", "IMAGE PART = ${it.headers}")
                }

                val response = repository.addProduct(
                    name = title.toPart(),
                    category = category.toPart(),
                    price = price.toPart(),
                    stock = stock.toPart(),
                    image = parts.firstOrNull(),
                    description = description.toPart()
                )

                loadProducts()
                onSuccess()

            } catch (e: Exception) {
                onError(e.message ?: "Upload failed")
            }
        }
    }
}