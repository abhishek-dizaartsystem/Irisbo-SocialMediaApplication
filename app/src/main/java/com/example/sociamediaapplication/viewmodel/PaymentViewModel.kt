package com.example.sociamediaapplication.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.PaymentRepository
import com.example.sociamediaapplication.model.request.OrderItem
import com.example.sociamediaapplication.model.request.VerifyPaymentRequest
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

    fun addOrderItem(order_items: List<OrderItem>){
        _orderItems.value = order_items
    }

    fun startPayment(
        activity: Activity,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {

                val order = repository.createOrder(orderItems.value).order

                val checkout = Checkout()
                checkout.setKeyID("rzp_test_SAOQiZzVCq4nYw")

                val options = JSONObject().apply {
                    put("name", "Irisbo")
                    put("description", "Order Payment")
                    put("currency", order.currency)
                    put("amount", order.amount)
                    put("order_id", order.id)

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
        signature: String
    ){
        viewModelScope.launch {
            try {

                repository.verifyPayment(
                    VerifyPaymentRequest(
                        razorpay_order_id = orderId,
                        razorpay_payment_id = paymentId,
                        razorpay_signature = signature
                    )
                )

            } catch (e: Exception) {
                Log.e("Payment Debug", e.message ?: e.toString())
            }
        }
    }
}