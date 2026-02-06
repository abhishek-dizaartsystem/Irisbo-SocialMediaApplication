package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun PaymentStep(
    onBack: ()-> Unit = {}
){
    val subtotal: String = "2558.0"
    val shipping: String = "0.00"
    val tax: String = "204.64"
    val total = subtotal.toFloat() + tax.toFloat() + shipping.toFloat()


    val shippingOptions = listOf("Standard","Express","Premium")

    var selectedShippingOption by remember { mutableStateOf("Standard") }

    val paymentOptions = listOf("Credit", "UPI", "COD")

    var selectedPaymentOption by remember { mutableStateOf("Credit") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        LazyColumn() {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.shipping_truck_svgrepo_com),
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Shipping Method",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                ShippingMethodCard(
                    selected = selectedShippingOption == shippingOptions[0],
                    onClick = {
                        selectedShippingOption = shippingOptions[0]
                    },
                    name = "Standard Shipping",
                    estimatedDays = "5-7",
                    price = 0
                )
                ShippingMethodCard(
                    selected = selectedShippingOption == shippingOptions[1],
                    onClick = {
                        selectedShippingOption = shippingOptions[1]
                    },
                    name = "Express Shipping",
                    estimatedDays = "3-4",
                    price = 15
                )
                ShippingMethodCard(
                    selected = selectedShippingOption == shippingOptions[2],
                    onClick = {
                        selectedShippingOption = shippingOptions[2]
                    },
                    name = "Overnight Shipping",
                    estimatedDays = "1-2",
                    price = 25
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.card_credit_finance_svgrepo_com),
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Payment Method",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                PaymentMethodCard(
                    name = "Credit/Debit Card",
                    selected = selectedPaymentOption == paymentOptions[0],
                    onClick = {
                        selectedPaymentOption = paymentOptions[0]
                    }
                )
                PaymentMethodCard(
                    name = "UPI Apps",
                    selected = selectedPaymentOption == paymentOptions[1],
                    onClick = {
                        selectedPaymentOption = paymentOptions[1]
                    }
                )
                PaymentMethodCard(
                    name = "Cash on delivery",
                    selected = selectedPaymentOption == paymentOptions[2],
                    onClick = {
                        selectedPaymentOption = paymentOptions[2]
                    }
                )
            }

            item {
                if(selectedPaymentOption == paymentOptions[0]){
                    AddressField(
                        label = "Card Number",
                        value = "1234 5678 9012 1314",
                        onValueChange = {},
                        keyboardType = KeyboardType.Phone
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Phone
                    AddressField(
                        label = "Name on Card",
                        value = "John Dow",
                        onValueChange = {}
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AddressField(
                            label = "Expiry Date",
                            value = "MM/YY",
                            onValueChange = {},
                            modifier = Modifier.weight(1f),
                        )
                        AddressField(
                            label = "CVV",
                            value = "012",
                            onValueChange = {},
                            keyboardType = KeyboardType.Phone,
                            modifier = Modifier.weight(1f)
                        )

                    }

                }

            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = LGrey   // light background like image
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Order Summary",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        SummaryRow("Subtotal", subtotal.toFloat())
                        SummaryRow("Shipping", shipping.toFloat(), isFree = shipping.toDouble() == 0.0)
                        SummaryRow("Tax", tax.toFloat())

                        Spacer(modifier = Modifier.height(12.dp))

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.4f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "$${"%.2f".format(total)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }

                }
            }
        }





    }


}

@Composable
fun PaymentMethodCard(
    onClick: () -> Unit = {},
    selected: Boolean = false,
    name: String = "Credit/Debit Card"
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Blue
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = name,
                fontSize = 16.sp,
                color = Black
            )

        }
    }
}

@Composable
fun SummaryRow(
    title: String,
    amount: Float,
    isFree: Boolean = false
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF5A6B85)
        )

        Text(
            text = if (isFree) "Free" else "$${"%.2f".format(amount)}",
            fontSize = 16.sp,
            color = Color(0xFF5A6B85)
        )
    }
}

@Composable
fun ShippingMethodCard(
    onClick: ()->Unit = {},
    name: String = "Standard Shipping",
    estimatedDays: String = "5-7",
    price: Int = 0,
    selected: Boolean = false
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        modifier = Modifier.padding(top = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected,
                    onClick = onClick,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Blue
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        color = Black
                    )
                    Text(
                        text = "$estimatedDays business days",
                        fontSize = 16.sp,
                        color = GreyTxt
                    )
                }
            }
            Text(
                text = "$$price",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentStepPreview(){
    PaymentStep()
}