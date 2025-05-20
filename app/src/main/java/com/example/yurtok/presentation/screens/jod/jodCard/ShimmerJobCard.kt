package com.example.yurtok.presentation.screens.jod.jodCard

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer


@Composable
fun ShimmerJobCard(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = alphaAnim))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(24.dp)
                        .background(Color.LightGray.copy(alpha = alphaAnim))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(16.dp)
                        .background(Color.LightGray.copy(alpha = alphaAnim))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(14.dp)
                        .background(Color.LightGray.copy(alpha = alphaAnim))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(24.dp)
                            .background(Color.LightGray.copy(alpha = alphaAnim), RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .height(24.dp)
                            .background(Color.LightGray.copy(alpha = alphaAnim), RoundedCornerShape(8.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.LightGray.copy(alpha = alphaAnim))
                )
            }
        }
    }
}

@Preview
@Composable
fun asd(){
    ShimmerJobCard()
}