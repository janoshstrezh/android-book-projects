package com.example.geoquizcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuizScreen()
        }
    }
}

@Composable
fun QuizScreen() {

    var currentIndex by remember { mutableStateOf(0) }

    val questionBank = listOf(
        Question(R.string.question_asia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_australia, true)
    )

    val context = LocalContext.current

    fun checkAnswer(userAnswer: Boolean ){
        if (userAnswer == questionBank[currentIndex].answer) Toast.makeText(context, R.string.correct_toast,
            Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, R.string.incorrect_toast,
            Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(questionBank[currentIndex].textResId),
            modifier = Modifier.padding(24.dp)
        )

        Row {
            Button(onClick = { checkAnswer(true) }) {
                Text(stringResource(R.string.true_button))
            }

            Button(onClick = { checkAnswer(false) }) {
                Text(stringResource(R.string.false_button))
            }
        }

        Button(onClick = {
            currentIndex = (currentIndex + 1) % questionBank.size
        }) {
            Text(stringResource(R.string.next_button))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = "Next"
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizScreen()
}

