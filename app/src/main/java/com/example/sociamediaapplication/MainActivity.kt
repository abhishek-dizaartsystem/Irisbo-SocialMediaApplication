package com.example.sociamediaapplication

import android.Manifest
import android.content.Intent
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
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.NavigationManager
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.PaymentRepository
import com.example.sociamediaapplication.view.navigation.AppDestination
import com.example.sociamediaapplication.view.navigation.AppNavGraph
import com.example.sociamediaapplication.viewmodel.PaymentViewModel
import com.example.sociamediaapplication.viewmodel.factory.PaymentViewModelFactory
import com.google.android.gms.ads.MobileAds
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

        handleIntent(intent)

        MobileAds.initialize(this)
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val data: Uri? = intent?.data
        if (data != null) {
            val pathSegments = data.pathSegments
            if (pathSegments.size >= 2) {
                val type = pathSegments[0]
                val id = pathSegments[1].toIntOrNull() ?: 0
                NavigationManager.pendingDestination = when (type) {
                    "post" -> AppDestination.Post(id)
                    "profile" -> AppDestination.Profile(id)
                    "video" -> AppDestination.Video(id)
                    "reel" -> AppDestination.Reel(id)
                    "event" -> AppDestination.Event(id)
                    else -> null
                }
            } else if (pathSegments.size == 1) {
                val type = pathSegments[0]
                val id = data.getQueryParameter("id")?.toIntOrNull() ?: 0
                NavigationManager.pendingDestination = when (type) {
                    "post" -> AppDestination.Post(id)
                    "profile" -> AppDestination.Profile(id)
                    "video" -> AppDestination.Video(id)
                    "reel" -> AppDestination.Reel(id)
                    "event" -> AppDestination.Event(id)
                    else -> null
                }
            }
        } else {
            val entityType = intent?.getStringExtra("entity_type")
            val entityId = intent?.getStringExtra("entity_id")?.toIntOrNull()

            NavigationManager.pendingDestination = when (entityType) {
                "video" -> AppDestination.Video(entityId ?: 0)
                "profile" -> AppDestination.Profile(entityId ?: 0)
                "post" -> AppDestination.Post(entityId ?: 0)
                "reel" -> AppDestination.Reel(entityId ?: 0)
                "event" -> AppDestination.Event(entityId ?: 0)
                else -> null
            }
        }

        Log.d(
            "NAV_DEBUG",
            "Parsed pendingDestination: ${NavigationManager.pendingDestination}"
        )
    }
}




@Preview(showBackground = true)
@Composable
fun MainPreview() {
    AppNavGraph()
}