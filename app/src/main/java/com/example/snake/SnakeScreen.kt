package com.example.snake

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.snake.ui.components.drawApple
import com.example.snake.ui.components.drawEye
import com.example.snake.ui.components.drawTail
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun SnakeScreen(
    snakeViewModel: SnakeViewModel
) {
    val snakeBody by snakeViewModel.body.collectAsState()
    val apple by snakeViewModel.apple.collectAsState()
    val lastMovedDirection by snakeViewModel.lastMovedDirection.collectAsState()
    val lastTailDirection by snakeViewModel.lastTailDirection.collectAsState()

    val columns = GameBoard.COLUMNS
    val rows = GameBoard.ROWS
    val boxSize = 24.dp
    val spacing = 1.dp

    LaunchedEffect(Unit) {
        while (true) {
            delay(450)
            snakeViewModel.move()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding()
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    val xModule = abs(dragAmount.x)
                    val yModule = abs(dragAmount.y)

                    val newDirection = when {
                        xModule > yModule -> if (dragAmount.x < 0) Direction.Left else Direction.Right
                        else -> if (dragAmount.y < 0) Direction.Top else Direction.Bottom
                    }
                    snakeViewModel.changeDirection(newDirection)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        val canvasHeight = ((columns + 1) * (boxSize + spacing).value).dp - spacing
        val canvasWidth = ((rows + 1) * (boxSize + spacing).value).dp - spacing

        Canvas(
            modifier = Modifier
                .height(canvasHeight)
                .width(canvasWidth)
                .background(Color.Cyan)
        ) {
            for (y in 0..columns) {
                for (x in 0..rows) {
                    val xOffset = x * (boxSize.toPx() + spacing.toPx())
                    val yOffset = y * (boxSize.toPx() + spacing.toPx())

                    val point = Point(x, y)

                    val isSnake = snakeBody.any { it == point }
                    val isHead = snakeBody.last() == point
                    val isTail = snakeBody.first() == point && snakeBody.size >= 3

                    if (isTail) {
                        drawTail(
                            direction = lastTailDirection,
                            boxSize = boxSize.toPx(),
                            xOffset = xOffset,
                            yOffset = yOffset
                        )
                    } else {
                        drawRoundRect(
                            color = if (isSnake) Color.Red else Color.Gray,
                            size = Size(boxSize.toPx(), boxSize.toPx()),
                            topLeft = Offset(x = xOffset, y = yOffset),
                            cornerRadius = if (isHead) CornerRadius(boxSize.toPx() / 4) else CornerRadius.Zero
                        )
                    }

                    if (isHead) {
                        drawEye(
                            direction = lastMovedDirection,
                            boxSize = boxSize.toPx(),
                            xOffset = xOffset,
                            yOffset = yOffset
                        )
                    }

                    if (apple == point) {
                        drawApple(
                            boxSize = boxSize.toPx(),
                            xOffset = xOffset,
                            yOffset = yOffset
                        )
                    }
                }
            }
        }
    }
}
