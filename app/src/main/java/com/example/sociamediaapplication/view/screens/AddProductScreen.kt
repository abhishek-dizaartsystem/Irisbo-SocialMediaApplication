package com.example.sociamediaapplication.view.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.Specification
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.CustomTextField
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

@Composable
fun AddProductScreen(
    viewModel: MarketplaceViewModel = viewModel(),
    category: String = "",
    onCategoryClick: () -> Unit = {},
    navBack: () -> Unit = {},
    productId: Int = 0
) {

    val scroll = rememberScrollState()

    val categoryTypes by viewModel.categoryTypes.collectAsState()

    val specs = rememberSaveable {
        mutableStateListOf(Specification("", ""))
    }

    val images = remember {
        mutableStateListOf<Uri>()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        val spaceLeft = 5 - images.size
        images.addAll(uris.take(spaceLeft))
    }

    val context = LocalContext.current

//    if(productId==0){
//        var titleState by remember { mutableStateOf("") }
//        var priceState by remember { mutableStateOf("") }
//        var discount by remember { mutableStateOf("") }
//        var categoryState by remember { mutableStateOf("Category") }
//        var categoryId by remember { mutableStateOf(0) }
//        var stockState by remember { mutableStateOf("") }
//        var descriptionState by remember { mutableStateOf("") }
//        var showDropdown by remember { mutableStateOf(false) }
//    }else{
        var titleState by remember { mutableStateOf("") }
        var priceState by remember { mutableStateOf("") }
        var discount by remember { mutableStateOf("") }
        var categoryState by remember { mutableStateOf("Category") }
        var categoryId by remember { mutableStateOf(0) }
        var stockState by remember { mutableStateOf("") }
        var descriptionState by remember { mutableStateOf("") }
        var showDropdown by remember { mutableStateOf(false) }
//    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        // 🔹 Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navBack) {
                Icon(
                    painter = painterResource(R.drawable.back_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = "Add Product",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .padding(horizontal = 16.dp)
        ) {

            // -----------------------------
            // PRODUCT IMAGES
            // -----------------------------
            Text("Product Images", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                // Add button (only if <5 images)
                if (images.size < 5) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, Grey, RoundedCornerShape(12.dp))
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.add_svgrepo_com),
                                contentDescription = null,
                                tint = GreyTxt,
                                modifier = Modifier.size(28.dp)
                            )
                            Text("Add", color = GreyTxt, fontSize = 12.sp)
                        }
                    }
                }

                // Preview images
                images.forEach { uri ->

                    Box(
                        modifier = Modifier.size(90.dp)
                    ) {

                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                        )

                        // remove button
                        IconButton(
                            onClick = { images.remove(uri) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(50))
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.add_svgrepo_com),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(14.dp)
                                    .rotate(45f) // makes + look like X
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(4.dp))
            Text("${images.size}/5 images", fontSize = 12.sp, color = Color.Gray)

            Spacer(Modifier.height(20.dp))
            // -----------------------------
            // TITLE
            // -----------------------------
            CustomTextField(
                label = "Product Title",
                value = titleState,
                placeHolder = "e.g. iPhone 14 Pro Max",
                onValueChange = { titleState = it }
            )

            Spacer(Modifier.height(16.dp))

            // -----------------------------
            // PRICE ROW
            // -----------------------------
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                CustomTextField(
                    modifier = Modifier.weight(1f),
                    label = "Price ($)",
                    value = priceState,
                    onValueChange = { priceState = it },
                    keyboardType = KeyboardType.Number
                )

                CustomTextField(
                    modifier = Modifier.weight(1f),
                    label = "Discount (%)",
                    value = discount,
                    onValueChange = { discount = it },
                    keyboardType = KeyboardType.Number
                )
            }

            Spacer(Modifier.height(16.dp))

            // -----------------------------
            // CATEGORY
            // -----------------------------
            Text("Category", fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(LGrey)
                    .clickable { onCategoryClick() }
                    .padding(16.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable{
                            showDropdown = true
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = categoryState,
                        color = if (categoryState == "Category") Color.Gray else Color.Black
                    )

                    Icon(
                        painter = painterResource(R.drawable.back_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(-90f)
                    )
                }
                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = {
                        showDropdown = false
                    }
                ) {
                    categoryTypes?.categories?.forEach { category->
                        DropdownMenuItem(
                            text = {
                                Text(category.name)
                            },
                            onClick = {
                                categoryState = category.name
                                categoryId = category.id
                                showDropdown = false
                            }
                        )
                    }

                }
            }

            Spacer(Modifier.height(16.dp))

            // -----------------------------
            // STOCK
            // -----------------------------
            CustomTextField(
                label = "Stock Quantity",
                value = stockState,
                onValueChange = {stockState = it},
                keyboardType = KeyboardType.Number
            )

            Spacer(Modifier.height(16.dp))

            // -----------------------------
            // DESCRIPTION
            // -----------------------------
            Text("Description", fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(6.dp))

            TextField(
                value = descriptionState,
                onValueChange = { descriptionState = it },
                placeholder = { Text("Describe your product...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    focusedContainerColor = LGrey,
                    unfocusedContainerColor = LGrey
                )
            )

            Spacer(Modifier.height(20.dp))

            // -----------------------------
            // SPECIFICATIONS (Dynamic)
            // -----------------------------
            Text("Specifications", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))

            Column(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(LGrey)
                    .padding(10.dp)
            ) {

                specs.forEachIndexed { index, item ->

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {

                        CustomTextField(
                            modifier = Modifier.weight(1f),
                            label = "Field",
                            value = item.key,
                            placeHolder = "e.g. Storage",
                            onValueChange = { newKey ->
                                specs[index] = item.copy(key = newKey)

                                if (index == specs.lastIndex &&
                                    (newKey.isNotBlank() || item.value.isNotBlank())
                                ) {
                                    specs.add(Specification("", ""))
                                }
                            }
                        )

                        CustomTextField(
                            modifier = Modifier.weight(1f),
                            label = "Value",
                            value = item.value,
                            placeHolder = "e.g. 256GB",
                            onValueChange = { newValue ->
                                specs[index] = item.copy(value = newValue)

                                if (index == specs.lastIndex &&
                                    (item.key.isNotBlank() || newValue.isNotBlank())
                                ) {
                                    specs.add(Specification("", ""))
                                }
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(26.dp))

            Button(
                onClick = {
                    viewModel.addProduct(
                        context = context,
                        title = titleState,
                        category = categoryId.toString(),
                        price = priceState,
                        stock = stockState,
                        description = descriptionState,
                        specs = specs.filter { it.key.isNotBlank() },
                        images = images,
                        onSuccess = { navBack() },
                        onError = { Log.e("UPLOAD", it) },
                        discount = discount
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("List Product", fontSize = 16.sp)
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun AddProductScreenPreview() {
    AddProductScreen()
}