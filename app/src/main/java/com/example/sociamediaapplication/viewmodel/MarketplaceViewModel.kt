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
import com.example.sociamediaapplication.model.ShippingPrice
import com.example.sociamediaapplication.model.Specification
import com.example.sociamediaapplication.model.request.AddToCartRequest
import com.example.sociamediaapplication.model.request.AddToWishlistRequest
import com.example.sociamediaapplication.model.response.CartResponse
import com.example.sociamediaapplication.model.response.ProductCategoriesType
import com.example.sociamediaapplication.model.response.ProductDetailsResponse
import com.example.sociamediaapplication.model.response.ReviewsResponse
import com.example.sociamediaapplication.model.response.UserProductsResponse
import com.example.sociamediaapplication.model.response.VendorProductsResponse
import com.example.sociamediaapplication.model.response.WishlistResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList
import kotlin.collections.map

class MarketplaceViewModel(
    private val repository: MarketplaceRepository
) : ViewModel() {



    //STATE
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    //PRODUCTS

    private val _productDetails = MutableStateFlow<ProductDetailsResponse?>(null)
    val productDetails: StateFlow<ProductDetailsResponse?> = _productDetails

    private val _vendorProducts = MutableStateFlow<VendorProductsResponse?>(null)
    val vendorProducts: StateFlow<VendorProductsResponse?> = _vendorProducts

    private val _userProducts = MutableStateFlow<UserProductsResponse?>(null)
    val userProducts: StateFlow<UserProductsResponse?> = _userProducts


    private val _wishListItems = MutableStateFlow<List<WishlistResponse>>(emptyList())
    val wishListItems: StateFlow<List<WishlistResponse>> = _wishListItems

    private val _cartItems = MutableStateFlow<List<CartResponse>>(emptyList())
    val cartItems: StateFlow<List<CartResponse>> = _cartItems

    private val _cartSum = MutableStateFlow<Double>(0.0)
    val cartSum: StateFlow<Double> = _cartSum

    // List/Nestedlist values

    private val _productReviews = MutableStateFlow<ReviewsResponse?>(null)
    val productReviews: StateFlow<ReviewsResponse?> = _productReviews


    //BACKEND MUTABLE VALUES
    private val _tax = MutableStateFlow<Double>(12.0)
    val tax: StateFlow<Double> = _tax

    private val _shippingType = MutableStateFlow<List<ShippingPrice>>(
        listOf(
            ShippingPrice(
                type = "Standard",
                price = 0.0,
                time = "5-7"
            ),
            ShippingPrice(
                type = "Express",
                price = 15.0,
                time = "3-4"
            ),
            ShippingPrice(
                type = "Premium",
                price = 25.0,
                time = "1-2"
            )
        )
    )
    val shippingType: StateFlow<List<ShippingPrice>> = _shippingType

    private val _categoryTypes = MutableStateFlow<ProductCategoriesType?>(null)
    val categoryTypes: StateFlow<ProductCategoriesType?> = _categoryTypes


    fun loadProductReviews(productId: Int){
        viewModelScope.launch {
            val reviews = repository.getReviews(productId)

            _productReviews.value = reviews
        }
    }

    fun addProductReview(
        productId: Int,
        rating: Int,
        comment: String
    ){
        viewModelScope.launch {
            repository.addReview(productId, rating, comment)
        }
    }




    fun loadCheckoutDetails(){
        viewModelScope.launch {
            val checkoutDetails = repository.fetchCheckoutDetails()

            _tax.value = checkoutDetails.tax
            _shippingType.value = checkoutDetails.shipping

        }
    }

    fun loadProductDetails(productId: Int){
        viewModelScope.launch {
            val details = repository.getProductDetails(productId)

            _productDetails.value = details
        }
    }

    fun loadProductCategoryTypes(){
        viewModelScope.launch {
            val categories = repository.getProductCategoryTypes()
            _categoryTypes.value = categories
        }
    }

    fun loadVendorProducts() {
        Log.d("API_DEBUG", "loadVendorProducts() called")

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {

                Log.d("API_DEBUG", "Calling repository.getProducts()")

                val products = repository.getVendorProducts()

                Log.d("API_DEBUG", "Raw product list size = ${products.products.size}")

                products.products.forEachIndexed { index, product ->
                    Log.d(
                        "API_DEBUG",
                        """
                    Product[$index]:
                    id = ${product.id}
                    name = ${product.name}
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

    fun loadUserProducts() {
        Log.d("API_DEBUG", "loadUserProducts() called")

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {

                Log.d("API_DEBUG", "Calling repository.getProducts()")

                val products = repository.getUserProducts()
                _userProducts.value = products
                Log.d("API_DEBUG", "StateFlow updated with products")
            }catch (e: Exception) {

                Log.e("API_DEBUG", "API FAILED: ${e.message}", e)

                _error.value = e.message ?: "Failed to load products"

            } finally {

                _isLoading.value = false
                Log.d("API_DEBUG", "Loading finished")

            }
        }
    }

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


    fun loadWishlist(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val wishList = repository.getWishlistProducts()
                _wishListItems.value = wishList
            }catch (e: Exception){
                Log.e("API_DEBUG", "WISHLIST LOAD FAILED: ${e.message}", e)
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun addToWishlist(
        productId: Int,
        onError: (String)-> Unit
    ){
        viewModelScope.launch {
            try {
                repository.addToWishlist(AddToWishlistRequest(productId))
                loadWishlist()
            }catch (e: Exception){
                onError(e.message ?: "Add to wishlist failed")
            }
        }
    }

    fun deleteWishlistProduct(
        productId: Int,
        onError: (String)-> Unit
    ){
        viewModelScope.launch {
            try {
                repository.removeFromWishlist(productId)
                loadWishlist()
            }catch (e: Exception){
                onError(e.message ?: "Delete from wishlist failed")
            }
        }
    }




    fun addProduct(
        context: Context,
        title: String,
        category:String,
        price: String,
        stock: String,
        description: String,
        specs: List<Specification>,
        discount: String,
        images: List<Uri>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val parts = images.map { uri ->

                    val file = uriToFile(uri, context)
                    val mime = getMimeType(context, uri)

                    fileToMultipart("product_images", file, mime)
                }

                val specsPart = Gson().toJson(specs).toPart()

                Log.d("UPLOAD_DEBUG", "TITLE = $title")
                Log.d("UPLOAD_DEBUG", "CATEGORY = $category")
                Log.d("UPLOAD_DEBUG", "PRICE = $price")
                Log.d("UPLOAD_DEBUG", "STOCK = $stock")
                Log.d("UPLOAD_DEBUG", "DESC = $description")
                Log.d("UPLOAD_DEBUG", "DESC = $discount")
                Log.d("UPLOAD_DEBUG", "IMAGES COUNT = ${images.size}")
                Log.d("UPLOAD_DEBUG", "SPECS = ${Gson().toJson(specs)}")

                parts.forEach {
                    Log.d("UPLOAD_DEBUG", "IMAGE PART = ${it.headers}")
                }

                repository.addProduct(
                    name = title.toPart(),
                    category = category.toPart(),
                    price = price.toPart(),
                    stock = stock.toPart(),
                    description = description.toPart(),
                    discount = discount.toPart(),
                    specs = specsPart,
                    images = parts
                )

                loadVendorProducts()
                onSuccess()

            } catch (e: Exception) {
                onError(e.message ?: "Upload failed")
            }
        }
    }


}