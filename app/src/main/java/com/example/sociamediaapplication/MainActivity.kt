package com.example.sociamediaapplication

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.PaymentRepository
import com.example.sociamediaapplication.view.navigation.AppNavGraph
import com.example.sociamediaapplication.viewmodel.PaymentViewModel
import com.example.sociamediaapplication.viewmodel.factory.PaymentViewModelFactory
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener


class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val tokenManager by lazy { TokenManager(applicationContext) }

    private val paymentViewModel: PaymentViewModel by viewModels {
        PaymentViewModelFactory(
            PaymentRepository(tokenManager)
        )
    }

    override fun onPaymentSuccess(
        razorpayPaymentId: String?,
        paymentData: PaymentData?
    ) {

        try {
            val json = paymentData?.data

            Log.d("RAZORPAY_DEBUG", paymentData?.data.toString())

            val orderId = json?.getString("razorpay_order_id")
            val paymentId = json?.getString("razorpay_payment_id")
            val signature = json?.getString("razorpay_signature")

            Log.d("RAZORPAY_DEBUG", "OrderId = ${ orderId ?: "No data" }")
            Log.d("RAZORPAY_DEBUG", "PaymentID = ${paymentId?:" No data"}")
            Log.d("RAZORPAY_DEBUG", "Signature = ${signature?:" No data"}")

            if (orderId != null && paymentId != null && signature != null) {

                paymentViewModel.verifyPayment(
                    orderId,
                    paymentId,
                    signature,
                    "upi"
                )

                Toast.makeText(this, "Payment Success ✅", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Missing payment data", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Error parsing payment data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentError(
        code: Int,
        response: String?,
        paymentData: PaymentData?
    ) {
        Toast.makeText(this, "Payment Failed: $response", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 🔥 NOTIFICATION PERMISSION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                1001
            )
        }

        setContent {
            AppNavGraph()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MainPreview() {
    AppNavGraph()
}