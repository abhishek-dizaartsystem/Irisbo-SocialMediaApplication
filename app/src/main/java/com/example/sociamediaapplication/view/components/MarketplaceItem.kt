package com.example.sociamediaapplication.view.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.TransparentWhite
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun MarketPlaceItem(
    productId: String = "1",
    painter: Int = R.drawable.gaming_chair,
    imageUrl: String? = null,
    price: Float = 180f,
    productName: String = "Nike Air Jordan 1",
    onClick: (String)-> Unit = {},
    onIconClick: (String)-> Unit = {},
    isLiked: Boolean = false,
    onLikeToggle: ()-> Unit = {}
){
    Card(
        onClick = {
            onClick(productId)
        },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                    .aspectRatio(1f)
            ) {

                // 🔥 NETWORK IMAGE OR FALLBACK
                if (imageUrl != null) {
                    Log.d("MarketPlaceItem Image Debug", "URL = $imageUrl")
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.aspectRatio(1f)
                    )
                } else {
                    Image(
                        painter = painterResource(painter),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.aspectRatio(1f)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { onIconClick(productId) },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = TransparentWhite
                        ),
                        modifier = Modifier
                            .size(30.dp)
                    ) {
                        Icon(
                            painter = if(isLiked) painterResource(R.drawable.heart_filled_svgrepo_com) else painterResource(R.drawable.heart_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(6.dp),
                            tint = if(isLiked) Red else Black
                        )
                    }
                }

            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = price.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal =12.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = productName,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal =12.dp)
            )
            Spacer(Modifier.height(12.dp))
        }

    }

}

@Preview(showBackground = true)
@Composable
fun MarketplaceItemPreview(){
    MarketPlaceItem()
}