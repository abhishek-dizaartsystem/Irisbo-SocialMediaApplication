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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue

@Composable
fun AddressStep(
    onNext: () -> Unit = {},
    fullName: String = "Abhishek",
    onFullNameChange: (String) -> Unit = {},
    phone: String = "1111111111",
    onPhoneChange: (String) -> Unit = {},
    address: String = "L-425",
    onAddressChange: (String) -> Unit = {},
    city: String = "Pratap Vihar, Ghaziabad",
    onCityChange: (String) -> Unit = {},
    state: String = "Uttar Pradesh",
    onStateChange: (String) -> Unit = {},
    zip: String = "201009",
    onZipChange: (String) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.location_pin_svgrepo_com),
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Shipping Address",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            item {
// Full Name
                AddressField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = onFullNameChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Phone
                AddressField(
                    label = "Phone Number",
                    value = phone,
                    onValueChange = onPhoneChange,
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Address
                AddressField(
                    label = "Address",
                    value = address,
                    onValueChange = onAddressChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // City + State Row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AddressField(
                        label = "City",
                        value = city,
                        onValueChange = onCityChange,
                        modifier = Modifier.weight(1f)
                    )

                    AddressField(
                        label = "State",
                        value = state,
                        onValueChange = onStateChange,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ZIP
                AddressField(
                    label = "ZIP Code",
                    value = zip,
                    onValueChange = onZipChange,
                    keyboardType = KeyboardType.Number
                )
            }
        }





    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddressStepPreview(){
    var fullName by remember { mutableStateOf("Abhishek Patwal") }
    var phone by remember { mutableStateOf("+919667207112") }
    var address by remember { mutableStateOf("L-425 Pratap Vihar Ghaziabad") }
    var city by remember { mutableStateOf("Ghaziabad") }
    var state by remember { mutableStateOf("Uttar Pradesh") }
    var zip by remember { mutableStateOf("201009") }

    AddressStep (
        fullName = fullName,
        onFullNameChange = { fullName = it },
        phone = phone,
        onPhoneChange = { phone = it },
        onAddressChange = { address = it },
        city = city,
        onCityChange = { city = it },
        state = state,
        onStateChange = { state = it },
        zip = zip
    ) { zip = it }
}