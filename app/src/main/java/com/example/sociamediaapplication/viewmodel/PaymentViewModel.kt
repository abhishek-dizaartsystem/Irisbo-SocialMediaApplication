package com.example.sociamediaapplication.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.PaymentRepository
import com.example.sociamediaapplication.model.request.CreateAddressRequest
import com.example.sociamediaapplication.model.request.OrderItem
import com.example.sociamediaapplication.model.request.VerifyPaymentRequest
import com.example.sociamediaapplication.model.response.AddressResponse
import com.razorpay.Checkout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class PaymentViewModel(
    private val repository: PaymentRepository
): ViewModel() {

    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems: StateFlow<List<OrderItem>> = _orderItems

    private val _addresses = MutableStateFlow<AddressResponse?>(null)
    val addresses: StateFlow<AddressResponse?> = _addresses

    fun addOrderItem(order_items: List<OrderItem>){
        _orderItems.value = order_items
    }

    fun startPayment(
        activity: Activity,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {

                Log.d("PAYMENT_FLOW", "Calling createOrder API with: ${orderId.value}")
                val order = repository.createOrder(orderId.value).data
                Log.d("PAYMENT_FLOW", "Success")

                val checkout = Checkout()
                checkout.setKeyID("rzp_test_SAOQiZzVCq4nYw")

                val options = JSONObject().apply {
                    put("name", "Irisbo")
                    put("description", "Order Payment")
                    put("currency", order.currency)
                    put("amount", order.amount)
                    put("order_id", order.razorpay_order_id)

                    put("prefill", JSONObject().apply {
                        put("email", "test@test.com")
                        put("contact", "9999999999")
                    })
                }

                checkout.open(activity, options)

            } catch (e: Exception) {
                onError(e.message ?: "Payment failed")
                Log.e("Payment_Debug", e.message ?: e.toString())
            }
        }
    }
    fun verifyPayment(
        orderId: String,
        paymentId: String,
        signature: String,
        payment_method: String
    ){
        viewModelScope.launch {
            try {

                repository.verifyPayment(
                    VerifyPaymentRequest(
                        razorpay_order_id = orderId,
                        razorpay_payment_id = paymentId,
                        razorpay_signature = signature,
                        payment_method = payment_method
                    )
                )

            } catch (e: Exception) {
                Log.e("Payment Debug", e.message ?: e.toString())
            }
        }
    }

    fun createAddress(
        full_name: String,
        phone: String,
        address_line1: String,
        address_line2: String,
        landmark: String,
        city: String,
        state: String,
        postal_code: String,
        country: String,
        address_type: String,
        is_default: Int
    ){
        viewModelScope.launch {
            try {
                repository.createAddress(
                    CreateAddressRequest(
                        full_name,
                        phone,
                        address_line1,
                        address_line2,
                        landmark,
                        city,
                        state,
                        postal_code,
                        country,
                        address_type,
                        is_default
                    )
                )
            }catch (e: Exception){
                Log.e("Payment_debug", e.message.toString())
            }
        }
    }

    fun fetchAddresses(){
        viewModelScope.launch {
            try {
                _addresses.value = repository.fetchAddresses()
            }catch (e: Exception){
                Log.e("Payment_debug", e.message.toString())
            }
        }
    }

    private val _orderId= MutableStateFlow<Int>(0)
    val orderId: StateFlow<Int> = _orderId

    fun generateOrder(address_id: Int){
        viewModelScope.launch {
            try {
                Log.d("PAYMENT_FLOW", "Used AddressID: ${address_id}")
                val response = repository.generateOrderId(address_id)
                Log.d("PAYMENT_FLOW", "Generated OrderId: ${response}")
                Log.d("PAYMENT_FLOW", "Generated OrderId: ${_orderId.value}")
                _orderId.value = response.data.id

                Log.d("PAYMENT_FLOW", "Generated OrderId: ${_orderId.value}")
            }catch (e: Exception){
                Log.e("Payment_debug", e.message.toString())
            }
        }
    }
}