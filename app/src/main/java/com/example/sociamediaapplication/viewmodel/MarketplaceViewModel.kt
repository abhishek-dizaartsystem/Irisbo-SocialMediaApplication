package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.MarketplaceRepository
import com.example.sociamediaapplication.data.utils.fileToMultipart
import com.example.sociamediaapplication.data.utils.getMimeType
import com.example.sociamediaapplication.data.utils.toPart
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.Specification
import com.example.sociamediaapplication.model.request.AddToCartRequest
import com.example.sociamediaapplication.model.response.CartResponse
import com.example.sociamediaapplication.model.response.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _cartItems = MutableStateFlow<List<CartResponse>>(emptyList())
    val cartItems: StateFlow<List<CartResponse>> = _cartItems

    private val _cartSum = MutableStateFlow<Double>(0.0)
    val cartSum: StateFlow<Double> = _cartSum

    fun loadCart(){
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val cart = repository.getCartProducts()
                _cartItems.value = cart

                _cartSum.value = cart.sumOf {
                    (it.price.toDoubleOrNull() ?: 0.0) * it.quantity
                }

            }catch (e: Exception){
                Log.e("API_DEBUG", "CART LOAD FAILED: ${e.message}", e)
                _error.value = e.message ?: "Failed to load cart"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToCart(
        productId: String,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            try{
                repository.addToCart(AddToCartRequest(productId, 1))
                loadCart()
            } catch (e: Exception) {
                onError(e.message ?: "Add to cart failed")
            }
        }
    }

    fun increaseProductQuantity(
        productId: Int,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            try {

                val currentItem = _cartItems.value.find { it.id == productId }
                val newQty = (currentItem?.quantity ?: 0) + 1

                repository.addToCart(
                    AddToCartRequest(productId.toString(), newQty)
                )

                loadCart()

            } catch (e: Exception) {
                onError(e.message ?: "Failed to update quantity")
            }
        }
    }

    fun decreaseProductQuantity(
        productId: Int,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {

                val currentItem = _cartItems.value.find { it.id == productId }
                    ?: return@launch

                val newQty = currentItem.quantity - 1

                if (newQty <= 0) {
                    repository.deleteCartProduct(productId.toString())
                } else {
                    repository.addToCart(
                        AddToCartRequest(productId.toString(), newQty)
                    )
                }

                loadCart()

            } catch (e: Exception) {
                onError(e.message ?: "Failed to update quantity")
            }
        }
    }

    fun deleteCartProduct(
        productId: Int,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            try{
                repository.deleteCartProduct(productId.toString())
                loadCart()
            } catch (e: Exception) {
                onError(e.message ?: "Delete from cart failed")
            }
        }
    }


    // -------------------------------
    // WISHLIST
    // -------------------------------
    private val _wishListItems = MutableStateFlow<List<ProductResponse>>(emptyList())
    val wishListItems: StateFlow<List<ProductResponse>> = _wishListItems






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

                repository.addProduct(
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