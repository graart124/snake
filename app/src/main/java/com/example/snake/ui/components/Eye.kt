package com.example.snake.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import com.example.snake.Direction

fun DrawScope.drawEye(direction: Direction, boxSize: Float, xOffset: Float, yOffset: Float) {
    val additionalOffset = calculateAdditionalOffsetToEye(direction, boxSize)
    val offset = Offset(
        xOffset + additionalOffset.x,
        yOffset + additionalOffset.y
    )
    drawCircle(
        color = Color.Blue,
        center = offset,
        style = Fill,
        radius = boxSize / 8f
    )
}

fun calculateAdditionalOffsetToEye(direction: Direction, boxSize: Float): Offset {
    return when (direction) {
        Direction.Top -> Offset(x = boxSize / 2, y = boxSize / 3)
        Direction.Bottom -> Offset(x = boxSize / 2, y = boxSize / 1.5f)
        Direction.Right -> Offset(x = boxSize / 1.5f, y = boxSize / 2)
        Direction.Left -> Offset(x = boxSize / 3, y = boxSize / 2)
    }
}
