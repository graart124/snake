package com.example.snake

object GameBoard {
    const val COLUMNS = 12
    const val ROWS = 20

    val board: Set<Point>
        get() {
            val gameBoard = mutableSetOf<Point>()
            for (y in 0..COLUMNS) {
                for (x in 0..ROWS) {
                    gameBoard.add(Point(x, y))
                }
            }
            return gameBoard
        }
}
