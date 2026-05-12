package com.example.sociamediaapplication.view.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.CategoryCard
import com.example.sociamediaapplication.viewmodel.VideoViewModel


@Composable
fun CategoryScreen(
    videoViewModel: VideoViewModel = viewModel(),
    onNavigate: () -> Unit = {}
) {

    var searchTxt by remember { mutableStateOf("") }
    val categories by videoViewModel.categories.collectAsState()
    val suggestions by videoViewModel.searchSuggestions.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .background(BackgroundColor)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(12.dp))
            TextField(
                value = searchTxt,
                onValueChange = {newMessage->
                    searchTxt = newMessage

                    if (newMessage.isNotBlank()) {
                        videoViewModel.searchVideos(newMessage)
                    }
                },
                placeholder = {
                    Text(
                        text = "Search Categories, videos...",
                        color = GreyTxt
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    unfocusedContainerColor = LGrey,
                    focusedContainerColor = LGrey
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = GreyTxt
                    )
                }
            )

            if (searchTxt.isNotBlank() && suggestions.isNotEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            LGrey,
                            RoundedCornerShape(12.dp)
                        )
                ) {

                    suggestions.distinct().take(6).forEach { title ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    searchTxt = title

                                    videoViewModel.searchVideos(title)

                                    onNavigate()
                                }
                                .padding(12.dp)
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.search_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = GreyTxt
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(text = title)
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .height(30.dp)
                            .widthIn(24.dp, 124.dp)
                    ) {
                        Text(
                            text = "Very Short",
                            modifier = Modifier.padding(horizontal = 14.dp),
                            fontSize = 16.sp
                        )
                    }
                }
                item {
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .height(30.dp)
                            .widthIn(24.dp, 124.dp)
                    ) {
                        Text(
                            text = "Short",
                            modifier = Modifier.padding(horizontal = 14.dp),
                            fontSize = 16.sp
                        )
                    }
                }
                item {
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .height(30.dp)
                            .widthIn(54.dp, 104.dp)
                    ) {
                        Text(
                            text = "Medium",
                            modifier = Modifier.padding(horizontal = 14.dp),
                            fontSize = 16.sp
                        )
                    }
                }

                item {
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .height(30.dp)
                            .widthIn(54.dp, 104.dp)
                    ) {
                        Text(
                            text = "Long",
                            modifier = Modifier.padding(horizontal = 14.dp),
                            fontSize = 16.sp
                        )
                    }
                }

                item {
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .height(30.dp)
                            .widthIn(54.dp, 104.dp)
                    ) {
                        Text(
                            text = "Very Long",
                            modifier = Modifier.padding(horizontal = 14.dp),
                            fontSize = 16.sp
                        )
                    }
                }


            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item{
                    CategoryCard(
                        id = 0,
                        name = "All",
                        onCategoryClick = {
                            videoViewModel.fetchAllVideos()
                            onNavigate()
                        }
                    )
                }

                items(categories?.data ?: emptyList()) {category->
                    CategoryCard(
                        id = category.id,
                        name = category.name,
                        onCategoryClick = {
                            Log.d("Category_DEBUG", category.id.toString())
                            videoViewModel.fetchVideosByCategory(category.id)

                            onNavigate()
                        }
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoryScreenPreview(){
    CategoryScreen()
}