package com.example.sociamediaapplication.view.components

import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun WishlistItem(
    productId: String = "1",
    productImage: Int,
    productName: String,
    sellerName: String,
    price: Float,
    onAddToCart: () -> Unit,
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
                    text = productName + productId,
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
                    text = price.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row() {
                    Button(
                        onClick = onAddToCart,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BackgroundColor
                        ),
                        border = BorderStroke(1.dp, LGrey),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.cart_shopping_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add to Cart",
                            color = Black
                        )
                    }

                    IconButton(
                        onClick = onDelete
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
fun WishlistItemPreview() {
    WishlistItem(
        productImage = R.drawable.iphone,
        productName = "iPhone 14 Pro Max",
        sellerName = "John Smith",
        price = 899f,
        onAddToCart = {},
        onDelete = {}
    )
}
