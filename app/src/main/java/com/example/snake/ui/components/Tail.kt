package com.example.snake.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.snake.Direction

fun DrawScope.drawTail(direction: Direction, boxSize: Float, xOffset: Float, yOffset: Float) {
    val path = Path().apply {
        when (direction) {
            Direction.Bottom -> {
                moveTo(xOffset + boxSize / 2, yOffset)
                lineTo(xOffset, yOffset + boxSize)
                lineTo(xOffset + boxSize, yOffset + boxSize)
            }

            Direction.Top -> {
                moveTo(xOffset + boxSize / 2, yOffset + boxSize)
                lineTo(xOffset, yOffset)
                lineTo(xOffset + boxSize, yOffset)
            }

            Direction.Left -> {
                moveTo(xOffset + boxSize, yOffset + boxSize / 2)
                lineTo(xOffset, yOffset)
                lineTo(xOffset, yOffset + boxSize)
            }

            Direction.Right -> {
                moveTo(xOffset, yOffset + boxSize / 2)
                lineTo(xOffset + boxSize, yOffset)
                lineTo(xOffset + boxSize, yOffset + boxSize)
            }
        }
        close()
    }

    drawRect(
        color = Color.Gray,
        size = Size(boxSize, boxSize),
        topLeft = Offset(x = xOffset, y = yOffset)
    )

    drawPath(
        color = Color.Red,
        path = path
    )
}
