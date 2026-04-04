package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    var countCorrectAnswers = 0
    var countAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)

        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }


        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }



    private fun blockedButtons(){
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }

    private fun updateButtons(){
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)

        if (questionBank[currentIndex].isAnswered){
            blockedButtons()
        }
        else {
            updateButtons()
        }



    }

    private fun checkAnswer(userAnswer: Boolean) {
        countAnswers++
        var messageResId = 0
        val correctAnswer = questionBank[currentIndex].answer
        blockedButtons()
        questionBank[currentIndex].isAnswered = true
        if (userAnswer == correctAnswer){
            messageResId = R.string.correct_toast
            countCorrectAnswers++
            }
            else messageResId = R.string.incorrect_toast

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if (countAnswers == questionBank.size) {
            nextButton.isEnabled = false
            printRes()
        }
    }

    fun printRes() {questionTextView.text = getString(R.string.result_text, countRes())}
    fun countRes() = (countCorrectAnswers.toDouble() / questionBank.size) * 100

    override fun onStart() {
        super.onStart()
        Log.d(TAG,
            "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,
            "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,
            "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG,
            "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,
            "onDestroy() called")
    }
}