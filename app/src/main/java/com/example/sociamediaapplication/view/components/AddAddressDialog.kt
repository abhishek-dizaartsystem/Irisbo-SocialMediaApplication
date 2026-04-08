package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddAddressDialog(
    onDismiss: () -> Unit,
    onSave: (
        fullName: String,
        phone: String,
        address1: String,
        address2: String,
        landmark: String,
        city: String,
        state: String,
        postalCode: String,
        country: String,
        addressType: String,
        isDefault: Int
    ) -> Unit
) {

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address1 by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("India") }
    var addressType by remember { mutableStateOf("Home") }
    var isDefault by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        fullName,
                        phone,
                        address1,
                        address2,
                        landmark,
                        city,
                        state,
                        postalCode,
                        country,
                        addressType,
                        if (isDefault) 1 else 0
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = {
            Text("Add Address", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                TextField(fullName, { fullName = it }, label = { Text("Full Name") })
                TextField(phone, { phone = it }, label = { Text("Phone") })

                TextField(address1, { address1 = it }, label = { Text("Address Line 1") })
                TextField(address2, { address2 = it }, label = { Text("Address Line 2") })

                TextField(landmark, { landmark = it }, label = { Text("Landmark") })

                TextField(city, { city = it }, label = { Text("City") })
                TextField(state, { state = it }, label = { Text("State") })

                TextField(postalCode, { postalCode = it }, label = { Text("Postal Code") })
                TextField(country, { country = it }, label = { Text("Country") })

                // 📍 Address Type Dropdown
                var expanded by remember { mutableStateOf(false) }

                Box {
                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(addressType)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("home", "work", "other").forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    addressType = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // ✅ Default Address Checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isDefault,
                        onCheckedChange = { isDefault = it }
                    )
                    Text("Set as default address")
                }
            }
        }
    )
}