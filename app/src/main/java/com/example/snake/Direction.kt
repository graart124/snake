package com.example.snake

enum class Direction {
    Top, Bottom, Right, Left
}

fun Direction.isOpposite(direction: Direction): Boolean {
    return (this == Direction.Top && direction == Direction.Bottom) ||
            (this == Direction.Bottom && direction == Direction.Top) ||
            (this == Direction.Left && direction == Direction.Right) ||
            (this == Direction.Right && direction == Direction.Left)
}
