package com.example.yurtok.presentation.screens.favorit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yurtok.R

@Composable
fun TextWithIcon() {
    val iconId = R.drawable.bookmark

    val text = buildAnnotatedString {
        append("Нажмите на ")
        appendInlineContent("icon", "[icon]") // Вставка иконки
        append(", чтобы добавить вакансию в избранное")
    }

    val inlineContent = mapOf(
        "icon" to InlineTextContent(
            placeholder = Placeholder(
                width = 16.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Избранное",
                modifier = Modifier.size(16.dp),
                tint = Color(0xFFFFFFFF)
            )
        }
    )

    Text(text = text, inlineContent = inlineContent, fontSize = 16.sp, color = Color.White)
}

@Composable
@Preview
fun add(){
    TextWithIcon()
}