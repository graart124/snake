package com.example.snake.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill

fun DrawScope.drawApple(boxSize: Float, xOffset: Float, yOffset: Float) {
    val path = Path().apply {
        moveTo(xOffset + boxSize / 2, yOffset + boxSize / 3)
        lineTo(xOffset + boxSize / 2, yOffset)
        lineTo(xOffset + boxSize / 1.6f, yOffset + boxSize / 5)
        close()
    }
    drawPath(
        color = Color.Green,
        path = path
    )
    drawCircle(
        color = Color.Green,
        center = Offset(
            x = xOffset + boxSize / 2,
            y = yOffset + boxSize / 1.6f
        ),
        style = Fill,
        radius = boxSize / 3f
    )
}
