package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.GreyBtn


@Composable
fun CategoryCard(
    id: Int = 0,
    name: String = "Category Title",
    onCategoryClick: ()->Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = GreyBtn, shape = RoundedCornerShape(12.dp))
            .clickable{
                onCategoryClick()
            }
    ){

        Box(
            modifier = Modifier
                .heightIn(50.dp, 220.dp)
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.rectangle_36),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.8f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryCardPreview(){
    CategoryCard()
}