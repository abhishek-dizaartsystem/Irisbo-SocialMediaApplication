package com.example.sociamediaapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.repository.PaymentRepository
import com.example.sociamediaapplication.ui.theme.SociaMediaApplicationTheme
import com.example.sociamediaapplication.view.navigation.AppNavGraph
import com.example.sociamediaapplication.view.screens.MainScreen
import com.example.sociamediaapplication.viewmodel.PaymentViewModel
import com.example.sociamediaapplication.viewmodel.factory.PaymentViewModelFactory
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
import com.example.sociamediaapplication.data.preferences.TokenManager



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

        val orderId = paymentData?.orderId
        val paymentId = paymentData?.paymentId
        val signature = paymentData?.signature

        if (orderId != null && paymentId != null && signature != null) {

            paymentViewModel.verifyPayment(
                orderId,
                paymentId,
                signature
            )

        } else {
            Toast.makeText(this, "Missing payment data", Toast.LENGTH_SHORT).show()
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