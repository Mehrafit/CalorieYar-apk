package com.example.ui.components

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StoreRepository
import com.example.model.WheelSlice
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.SecondaryGold
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun LuckyWheelCanvas(
    isSpinning: Boolean,
    onSpinStart: () -> Unit,
    onSpinEnd: (WheelSlice) -> Unit,
    modifier: Modifier = Modifier
) {
    val slices = StoreRepository.wheelSlices
    val sliceAngle = 360f / slices.size
    val rotationAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.size(310.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationAnim.value)
                .testTag("lucky_wheel_canvas")
        ) {
            val radius = size.minDimension / 2f - 16f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Draw Outer Golden Rim
            drawCircle(
                color = SecondaryGold,
                radius = radius + 12f,
                center = center,
                style = Stroke(width = 10f)
            )

            // Draw Rim Lights (16 golden dots)
            for (i in 0 until 16) {
                val dotAngle = i * (360f / 16) * (PI / 180f)
                val dotX = center.x + (radius + 6f) * cos(dotAngle).toFloat()
                val dotY = center.y + (radius + 6f) * sin(dotAngle).toFloat()
                drawCircle(
                    color = if (i % 2 == 0) Color.White else SecondaryGold,
                    radius = 3.5f,
                    center = Offset(dotX, dotY)
                )
            }

            // Draw Wheel Slices
            slices.forEachIndexed { index, slice ->
                val startAngle = index * sliceAngle - 90f - (sliceAngle / 2f)

                drawArc(
                    brush = Brush.radialGradient(
                        colors = listOf(slice.primaryColor, slice.secondaryColor),
                        center = center,
                        radius = radius
                    ),
                    startAngle = startAngle,
                    sweepAngle = sliceAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )

                val lineAngle = (startAngle) * (PI / 180f)
                val lineX = center.x + radius * cos(lineAngle).toFloat()
                val lineY = center.y + radius * sin(lineAngle).toFloat()
                drawLine(
                    color = SecondaryGold.copy(alpha = 0.8f),
                    start = center,
                    end = Offset(lineX, lineY),
                    strokeWidth = 2.5f
                )

                drawIntoCanvas { canvas ->
                    val textAngle = (index * sliceAngle - 90f) * (PI / 180f)
                    val textRadius = radius * 0.65f
                    val textX = center.x + textRadius * cos(textAngle).toFloat()
                    val textY = center.y + textRadius * sin(textAngle).toFloat()

                    canvas.save()
                    canvas.nativeCanvas.rotate(
                        (index * sliceAngle).toFloat(),
                        textX,
                        textY
                    )

                    val paint = Paint().apply {
                        color = Color.White.toArgb()
                        textSize = 28f
                        isFakeBoldText = true
                        textAlign = Paint.Align.CENTER
                        isAntiAlias = true
                        setShadowLayer(4f, 1f, 1f, Color.Black.toArgb())
                    }

                    canvas.nativeCanvas.drawText(
                        slice.title,
                        textX,
                        textY + 10f,
                        paint
                    )
                    canvas.restore()
                }
            }

            // Inner Center Rim
            drawCircle(
                color = ForestGreenDark,
                radius = 38f,
                center = center
            )
            drawCircle(
                color = SecondaryGold,
                radius = 38f,
                center = center,
                style = Stroke(width = 4f)
            )
        }

        // Center Spin Trigger Button
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .shadow(8.dp, CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(SecondaryGold, AccentOrange)
                    )
                )
                .clickable(enabled = !isSpinning) {
                    if (!isSpinning) {
                        onSpinStart()
                        scope.launch {
                            val targetIndex = Random.nextInt(slices.size)
                            val winningSlice = slices[targetIndex]

                            val extraRotations = 5 * 360
                            val targetSliceAngle = (360f - (targetIndex * sliceAngle)) % 360f
                            val currentAngle = rotationAnim.value % 360f
                            val totalDelta = extraRotations + (targetSliceAngle - currentAngle)

                            rotationAnim.animateTo(
                                targetValue = rotationAnim.value + totalDelta,
                                animationSpec = tween(
                                    durationMillis = 4000,
                                    easing = FastOutSlowInEasing
                                )
                            )
                            onSpinEnd(winningSlice)
                        }
                    }
                }
                .testTag("wheel_center_spin_button"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isSpinning) "..." else "بچرخون!",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = ForestGreenDark
            )
        }

        // Top Needle / Arrow Indicator
        Canvas(
            modifier = Modifier
                .size(34.dp, 38.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-6).dp)
        ) {
            val path = Path().apply {
                moveTo(size.width / 2f, size.height)
                lineTo(0f, 0f)
                lineTo(size.width, 0f)
                close()
            }
            drawPath(
                path = path,
                brush = Brush.verticalGradient(listOf(Color(0xFFFFD54F), SecondaryGold))
            )
            drawPath(
                path = path,
                color = ForestGreenDark,
                style = Stroke(width = 2.5f)
            )
        }
    }
}
