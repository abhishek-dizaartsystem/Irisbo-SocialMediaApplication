package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.model.editor.TextLayer
import com.example.sociamediaapplication.ui.theme.Akronim
import com.example.sociamediaapplication.ui.theme.BabyPlums
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.BrownieStencil
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.HappySwirly
import com.example.sociamediaapplication.ui.theme.LoveDays
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.SansSeriff
import com.example.sociamediaapplication.ui.theme.ShinyCrystal
import com.example.sociamediaapplication.ui.theme.TransparentBlack
import com.example.sociamediaapplication.ui.theme.TransparentWhite
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.ui.theme.Yellow

@Composable
fun TextEditorControls(
    textLayer: TextLayer,
    onTextColorChange: (Color) -> Unit,
    onTextSizeChange: (Float) -> Unit,
    onFontFamilyChange: (FontFamily) -> Unit
) {

    var selectedCategory by remember { mutableStateOf("Color") }

    val categories = listOf("Color", "Size", "Style")

    var selectedTextColor by remember { mutableStateOf(Black) }

    Column(
        modifier = Modifier
            .background(TransparentWhite)
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {

        // 🔥 Category Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(categories) { category ->
                Text(
                    text = category,
                    color = if (category == selectedCategory) Black else GreyTxt,
                    modifier = Modifier
                        .clickable { selectedCategory = category },
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔥 Options Row
        when (selectedCategory) {

            "Color" -> {
                val colors = listOf(
                    White,
                    Black,
                    Red,
                    Blue,
                    Green,
                    Yellow
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(colors) { color ->

                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(28.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if(selectedTextColor!=color) TransparentBlack else White
                            )
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(color, CircleShape)
                                    .clickable {
                                        selectedTextColor = color
                                        onTextColorChange(color)
                                    }
                            )

                        }

                    }
                }
            }

            "Size" -> {
                val sizes = listOf(18f, 24f, 32f, 40f)

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(sizes) { size ->
                        Text(
                            text = size.toInt().toString(),
                            color = White,
                            modifier = Modifier.clickable {
                                onTextSizeChange(size)
                            },
                            fontSize = 16.sp
                        )
                    }
                }
            }

            "Style" -> {
                val fontOptions = listOf(
                    SansSeriff, BabyPlums, BrownieStencil, ShinyCrystal, Akronim, HappySwirly, LoveDays,
                )


                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(fontOptions) { font ->

                        Text(
                            text = "Text",
                            fontFamily = font,
                            fontSize = 22.sp,
                            color = White,
                            modifier = Modifier.clickable {
                                onFontFamilyChange(font)
                            }
                        )
                    }
                }
            }
        }
    }
}
