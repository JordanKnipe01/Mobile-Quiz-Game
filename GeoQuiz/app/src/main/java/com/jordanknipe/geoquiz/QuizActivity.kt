package com.jordanknipe.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
class QuizActivity : AppCompatActivity() {
    //Static Variables
    companion object {
        private const val TAG = "QuizActivity"
        private const val KEY_INDEX = "index"
        private const val REQUEST_CODE_CHEAT = 0
        private var mCurrentIndex = 0
        private var mIsCheater = false

    }
    private val myQuestionBank = arrayOf(Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Log.d(TAG,"onCreate(Bundle) called")

        if(savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0)
        }
        //Buttons&GUI
        val mTrueButton = findViewById<Button>(R.id.true_button)
        val mFalseButton = findViewById<Button>(R.id.false_button)
        val mNextButton = findViewById<ImageButton>(R.id.next_button)
        val mPrevButton = findViewById<ImageButton>(R.id.prev_button)
        val mQuestionTextView = findViewById<TextView>(R.id.question_text_view)
        val mCheatButton = findViewById<Button>(R.id.cheat_button)

        //Button onClick Listeners
        mCheatButton.setOnClickListener {
            val answerIsTrue: Boolean = myQuestionBank[mCurrentIndex].answerTrue
            val intent = CheatActivity.newIntent(this@QuizActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        mTrueButton.setOnClickListener {
           checkAnswer(true)
        }
        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }
        mNextButton.setOnClickListener {

                mCurrentIndex = (mCurrentIndex + 1) % mQuestionTextView.length()
                val question = myQuestionBank[mCurrentIndex].textResId
                mQuestionTextView.setText(question)

        }
        mPrevButton.setOnClickListener {
            if(mCurrentIndex!=0) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionTextView.length()
            }
            val question = myQuestionBank[mCurrentIndex].textResId
            mQuestionTextView.setText(question)

        }

        val question = myQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)

    }

    //States

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, mCurrentIndex)
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume()
    {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause()
    {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop()
    {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy()
    {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    //Methods
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) return
            mIsCheater = CheatActivity.wasAnswerShown(data)
        }
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue: Boolean = myQuestionBank[mCurrentIndex].answerTrue

        val messageResId: Int =
            when {
                // Checks if user has cheated before checking if their answer is correct
                mIsCheater -> R.string.judgement_toast
                userPressedTrue == answerIsTrue -> R.string.correct_toast
                else -> R.string.incorrect_toast
            }

        Toast.makeText(this@QuizActivity,
            messageResId,
            Toast.LENGTH_SHORT).show()
    }
}
