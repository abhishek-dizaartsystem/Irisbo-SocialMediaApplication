package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun CartItem(
    productId: Int = 1,
    productImage: Int,
    productName: String,
    sellerName: String,
    price: Float = 899f,
    quantity: Int = 1,
    onIncreaseQuantity: () -> Unit = {},
    onDecreaseQuantity: () -> Unit = {},
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Product Image
            Image(
                painter = painterResource(productImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Middle Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "$productName $productId",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "by $sellerName",
                    fontSize = 14.sp,
                    color = GreyTxt
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$$price",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {onDecreaseQuantity()},
                            Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.subtract_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Black
                            )
                        }

                        Text(
                            text = quantity.toString(),
                            fontSize = 16.sp,
                            color = Black,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = {onIncreaseQuantity()},
                            Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.add_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Black
                            )

                        }


                    }



                    IconButton(
                        onClick = onDelete,
                        Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.delete_svgrepo_com),
                            contentDescription = null,
                            tint = Red,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    CartItem(
        productImage = R.drawable.iphone,
        productName = "iPhone 14 Pro Max",
        sellerName = "John Smith",
        onDelete = {}
    )
}
