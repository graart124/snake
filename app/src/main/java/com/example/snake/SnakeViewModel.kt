package com.example.snake

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SnakeViewModel : ViewModel() {
    private val _body = MutableStateFlow(setOf(Point(0, 0)))
    val body: StateFlow<Set<Point>> = _body

    private val _apple = MutableStateFlow<Point?>(null)
    val apple: StateFlow<Point?> = _apple

    private var direction = Direction.Right

    private val _lastMovedDirection = MutableStateFlow(direction)
    val lastMovedDirection: StateFlow<Direction> = _lastMovedDirection

    private val _lastTailDirection = MutableStateFlow(direction)
    val lastTailDirection: StateFlow<Direction> = _lastTailDirection

    private var appleWasEaten = false

    init {
        startGame()
    }

    private fun startGame() {
        _body.value = setOf(Point(0, 0))
        direction = Direction.Right
        appleWasEaten = false
        generateApple()
    }

    fun move() {
        val tail = _body.value.first()
        var headAfterMove = _body.value.last()

        headAfterMove = when (direction) {
            Direction.Top -> headAfterMove.copy(y = headAfterMove.y - 1)
            Direction.Bottom -> headAfterMove.copy(y = headAfterMove.y + 1)
            Direction.Right -> headAfterMove.copy(x = headAfterMove.x + 1)
            Direction.Left -> headAfterMove.copy(x = headAfterMove.x - 1)
        }

        headAfterMove = wrapAround(headAfterMove)
        checkIfAppleEaten(headAfterMove)

        val bodyAfterMove = _body.value.toMutableSet()

        if (appleWasEaten) {
            appleWasEaten = false
            generateApple()
        } else {
            bodyAfterMove.remove(tail)
            updateLastTailDirection(tail, bodyAfterMove.firstOrNull() ?: tail)
        }

        if (isGameEnd(headAfterMove)) {
            startGame()
            return
        }

        bodyAfterMove.add(headAfterMove)

        _body.value = bodyAfterMove
        _lastMovedDirection.value = direction
    }

    private fun wrapAround(point: Point): Point {
        var x = point.x
        var y = point.y

        if (x > GameBoard.ROWS) x = 0 else if (x < 0) x = GameBoard.ROWS
        if (y > GameBoard.COLUMNS) y = 0 else if (y < 0) y = GameBoard.COLUMNS

        return Point(x, y)
    }

    private fun updateLastTailDirection(tail: Point, newTail: Point) {
        _lastTailDirection.value = when {
            tail.x > newTail.x -> Direction.Left
            tail.x < newTail.x -> Direction.Right
            tail.y > newTail.y -> Direction.Top
            else -> Direction.Bottom
        }
    }

    private fun isGameEnd(headAfterMove: Point): Boolean {
        return _body.value.contains(headAfterMove)
    }

    private fun checkIfAppleEaten(headAfterMove: Point) {
        if (_apple.value == headAfterMove) {
            appleWasEaten = true
        }
    }

    fun changeDirection(newDirection: Direction) {
        if (!newDirection.isOpposite(_lastMovedDirection.value) || _body.value.size == 1) {
            direction = newDirection
        }
    }

    private fun generateApple() {
        val gameBoardWithoutSnake = GameBoard.board.toMutableSet().apply {
            removeAll(_body.value)
            remove(_apple.value)
        }

        _apple.value = gameBoardWithoutSnake.randomOrNull()
    }
}

data class Point(val x: Int, val y: Int)
