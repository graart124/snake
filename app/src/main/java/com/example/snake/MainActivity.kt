package com.example.snake

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.snake.ui.theme.SnakeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val snakeViewModel = ViewModelProvider.create(this)[SnakeViewModel::class]
        enableEdgeToEdge()
        setContent {
            SnakeTheme {
                SnakeScreen(snakeViewModel)
            }
        }
    }
}
