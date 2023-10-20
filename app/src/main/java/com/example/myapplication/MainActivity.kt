package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGame()
        }
    }
}

@Composable
fun NumberGuessingGame() {
    var minRange by remember { mutableStateOf(0) }
    var maxRange by remember { mutableStateOf(100) }
    var sliderValue by remember { mutableStateOf((minRange + maxRange) / 2) }
    var targetValue by remember { mutableStateOf(Random.nextInt(minRange, maxRange + 1))}

    var totalScore by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var roundsPlayed by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("Score: " ) }
    var secondMessage by remember { mutableStateOf(" " ) }

    fun generateRandomTarget(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1)
    }

    fun checkProximity(): Int {
        val difference = Math.abs(sliderValue - targetValue)
        return when {
            difference <= 3 -> 5
            difference <= 8 -> 1
            else -> 0
        }
    }

    fun startNewRound() {
        targetValue = generateRandomTarget(minRange, maxRange)
        sliderValue = (minRange + maxRange) / 2
        roundsPlayed++
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bull's Eye Game", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Move the slider as close as you can to: $targetValue", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = sliderValue.toFloat(),
            onValueChange = { newValue -> sliderValue = newValue.toInt() },
            valueRange = minRange.toFloat()..maxRange.toFloat()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val points = checkProximity()
                score += points
                totalScore += points
                message = "Your Score: $score\n"

                if (points > 0) {
                    secondMessage = "\nPerfect! You scored $points point(s)."
                } else {
                    secondMessage = "\nTry again to get closer."
                }

                startNewRound()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Hit Me!")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = secondMessage,
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NumberGuessingGame()
}