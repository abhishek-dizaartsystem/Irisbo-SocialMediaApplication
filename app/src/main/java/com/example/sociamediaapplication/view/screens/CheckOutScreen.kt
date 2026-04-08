package com.example.sociamediaapplication.view.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.response.Address
import com.example.sociamediaapplication.ui.theme.*
import com.example.sociamediaapplication.view.components.AddAddressDialog
import com.example.sociamediaapplication.view.components.CartItem
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel
import com.example.sociamediaapplication.viewmodel.PaymentViewModel

@Composable
fun CheckOutScreen(
    viewModel: MarketplaceViewModel = viewModel(),
    paymentViewModel: PaymentViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {

    val cartItems by viewModel.cartItems.collectAsState()
    val addresses by paymentViewModel.addresses.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedAddress by remember { mutableStateOf<Address?>(null) }
    var selectedAddressId by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    val activity = LocalActivity.current

    // 🔁 Fetch addresses when screen loads
    LaunchedEffect(Unit) {
        paymentViewModel.fetchAddresses()
    }

    val orderId by paymentViewModel.orderId.collectAsState()

    LaunchedEffect(orderId) {

        Log.d("PAYMENT_FLOW", "Observed OrderId: $orderId")

        if (orderId != 0 && activity != null) {
            paymentViewModel.startPayment(
                activity = activity,
                onError = {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    Scaffold(
        topBar = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Text(
                        text = "Checkout",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider()
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider()

                Button(
                    onClick = {
                        if (selectedAddress == null) {
                            Toast.makeText(
                                activity,
                                "Please select an address",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            paymentViewModel.generateOrder(selectedAddressId)


                            Toast.makeText(
                                activity,
                                "Order placed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Continue")
                }
            }
        },
        containerColor = BackgroundColor
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // 🛒 CART ITEMS
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cartItems?.items ?: emptyList()) { item ->
                    CartItem(
                        productId = item.id,
                        productImage = item.product_image,
                        productName = item.name,
                        sellerName = item.seller_username ?: "Seller",
                        price = item.final_price.toFloat(),
                        quantity = item.quantity,
                        onIncreaseQuantity = {},
                        onDecreaseQuantity = {},
                        onDelete = {}
                    )
                }
            }

            // 📍 ADDRESS SECTION
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Select Address",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box {
                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedAddress?.address_line1 ?: "Choose Address"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {

                        // ➕ Add Address
                        DropdownMenuItem(
                            text = { Text("+ Add New Address") },
                            onClick = {
                                expanded = false
                                showDialog = true
                            }
                        )

                        // 📍 Existing addresses
                        addresses?.data?.forEach { address ->
                            DropdownMenuItem(
                                text = {
                                    Text("${address.address_line1}, ${address.city}")
                                },
                                onClick = {
                                    selectedAddress = address
                                    selectedAddressId = address.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // ➕ ADD ADDRESS DIALOG
        if (showDialog) {
            AddAddressDialog(
                onDismiss = { showDialog = false },
                onSave = { fullName, phone, address1, address2, landmark,
                           city, state, postalCode, country, type, isDefault ->

                    paymentViewModel.createAddress(
                        full_name = fullName,
                        phone = phone,
                        address_line1 = address1,
                        address_line2 = address2,
                        landmark = landmark,
                        city = city,
                        state = state,
                        postal_code = postalCode,
                        country = country,
                        address_type = type,
                        is_default = isDefault
                    )

                    paymentViewModel.fetchAddresses()
                    showDialog = false
                }
            )
        }
    }
}