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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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

    // ---------- STATE ----------
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    var correctAnswersCount by rememberSaveable { mutableIntStateOf(0) }

    val questionBank = rememberSaveable {
        mutableStateListOf(
            Question(R.string.question_asia, true),
            Question(R.string.question_africa, false),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_australia, true)
        )
    }

    // ---------- DERIVED STATE ----------
    val isAllAnswers = questionBank.all { it.isAnswered }

    fun countRes(): Int {
        val correct = correctAnswersCount
        val total = questionBank.size
        return (correct * 100) / total
    }

    val textToShow =
        if (!isAllAnswers)
            stringResource(questionBank[currentIndex].textResId)
        else
            stringResource(R.string.result_text, countRes())

    // ---------- LOGIC ----------
    val context = LocalContext.current

    fun checkAnswer(userAnswer: Boolean) {
        questionBank[currentIndex] =
            questionBank[currentIndex].copy(isAnswered = true)

        if (userAnswer == questionBank[currentIndex].answer) {
            correctAnswersCount++
            Toast.makeText(context, R.string.correct_toast, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }

    fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    // ---------- UI ----------
    QuizContent(
        textToShow = textToShow,
        isAnswered = questionBank[currentIndex].isAnswered,
        isAllAnswers = isAllAnswers,
        onTrueClick = { checkAnswer(true) },
        onFalseClick = { checkAnswer(false) },
        onNextClick = { nextQuestion() }
    )
}

@Composable
fun QuizContent(
    textToShow: String,
    isAnswered: Boolean,
    isAllAnswers: Boolean,
    onTrueClick: () -> Unit,
    onFalseClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        QuestionText(textToShow)

        AnswerButtons(
            isAnswered = isAnswered,
            onTrueClick = onTrueClick,
            onFalseClick = onFalseClick
        )

        NextButton(
            isAllAnswers = isAllAnswers,
            onNextClick = onNextClick
        )
    }
}

@Composable
fun QuestionText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(24.dp)
    )
}

@Composable
fun AnswerButtons(
    isAnswered: Boolean,
    onTrueClick: () -> Unit,
    onFalseClick: () -> Unit
) {
    Row {
        Button(
            onClick = onTrueClick,
            enabled = !isAnswered
        ) {
            Text(stringResource(R.string.true_button))
        }

        Button(
            onClick = onFalseClick,
            enabled = !isAnswered
        ) {
            Text(stringResource(R.string.false_button))
        }
    }
}

@Composable
fun NextButton(
    isAllAnswers: Boolean,
    onNextClick: () -> Unit
) {
    Button(
        onClick = onNextClick,
        enabled = !isAllAnswers
    ) {
        Text(stringResource(R.string.next_button))
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = "Next"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizScreen()
}

