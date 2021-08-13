package com.jordanknipe.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.app.Activity

class CheatActivity : AppCompatActivity() {
    //Static Variables
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }
        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        }
        const val EXTRA_ANSWER_SHOWN: String = "com.jordanknipe.android.geoquiz.answer_shown"
        const val EXTRA_ANSWER_IS_TRUE: String = "com.jordanknipe.android.geoquiz.answer_is_true"
    }
    //Variables
    private var mAnswerIsTrue: Boolean = false
    private lateinit var mAnswerTextView: TextView
    private lateinit var mShowAnswerButton: Button
    //Methods
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(RESULT_OK, data)
    }
    //States
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        mAnswerIsTrue = intent.getBooleanExtra(Companion.EXTRA_ANSWER_IS_TRUE, false)

        mAnswerTextView = findViewById(R.id.answer_text_view)

        mShowAnswerButton = findViewById(R.id.show_answer_button)
        mShowAnswerButton.setOnClickListener {
            mAnswerTextView.setText(
                if (mAnswerIsTrue) R.string.true_button
                else R.string.false_button
            )
            setAnswerShownResult(true)


        }
    }
}