package com.malsoryz.memoryz.layout

import android.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malsoryz.memoryz.ui.theme.MemoryzTheme

@Composable
fun HueBar(
    modifier: Modifier = Modifier,
    onHueChanged: (Float) -> Unit
) {
    var selectRange by remember { mutableFloatStateOf(0f) }

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.Red)
            .padding(horizontal = 15.dp)
            .height(30.dp)
            .width(300.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    selectRange = (selectRange + dragAmount).coerceIn(0f, size.width.toFloat())
                    val hue = (selectRange / size.width) * 360f
                    onHueChanged(hue)
                }
            }
    ) {
        val hueColors = (0..360 step 15).map { h ->
            Color.hsv(h.toFloat(), 1f, 1f)
        }
        drawRect(
            brush = Brush.horizontalGradient(hueColors),
            size = size
        )
        drawCircle(
            color = Color.White,
            radius = 30f,
            center = Offset(selectRange, size.height / 2),
        )
        drawCircle(
            color = Color.hsv((selectRange / size.width) * 360f, 1f, 1f),
            radius = 20f,
            center = Offset(selectRange, size.height / 2),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorPickersPreview() {
    MemoryzTheme {
        var selectedHue by remember { mutableFloatStateOf(0f) }

        HueBar(
            onHueChanged = { hue -> 
                selectedHue = hue
            }
        )
    }
}