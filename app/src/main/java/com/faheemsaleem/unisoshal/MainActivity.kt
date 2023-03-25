package com.faheemsaleem.unisoshal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat

class MainActivity : AppCompatActivity() {

    lateinit var peopleImage : ImageView
    lateinit var welcomeText : TextView
    lateinit var descText : TextView
    lateinit var getStartedButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        peopleImage = findViewById<ImageView>(R.id.imageView2)
        welcomeText = findViewById<TextView>(R.id.textView)
        descText = findViewById<TextView>(R.id.textView2)
        getStartedButton = findViewById<Button>(R.id.button)

        // detect which activity started this activity
        val intent = intent
        val activityName = intent.getStringExtra("activityName")
        if (activityName == "SplashActivity")
        {
            // if the activity that started this activity was SplashActivity, animate the views
            animateViews()
        }
        else
        {
            // if the activity that started this activity was not SplashActivity, reset the views
            resetViews()
        }

        // give the button an onClickListener
        getStartedButton.setOnClickListener {
           // animate the rest of the items off the screen with fade out

            val logo = findViewById<ImageView>(R.id.logo)
            // move logo to the center of the device
            logo.animate().translationYBy(800f).setDuration(500).scaleX(1.1f).scaleY(1.1f).setInterpolator(android.view.animation.OvershootInterpolator(0.8f)).startDelay = 100
            peopleImage.animate().alpha(0f).translationYBy(200f).setDuration(500).setInterpolator(android.view.animation.OvershootInterpolator(0.8f)).startDelay = 100
            welcomeText.animate().alpha(0f).translationYBy(200f).setDuration(500).setInterpolator(android.view.animation.OvershootInterpolator(0.8f)).startDelay = 100
            descText.animate().alpha(0f).translationYBy(200f).setDuration(500).setInterpolator(android.view.animation.OvershootInterpolator(0.8f)).startDelay = 100
            getStartedButton.animate().alpha(0f).translationYBy(200f).setDuration(500).setInterpolator(android.view.animation.OvershootInterpolator(0.2f)).withEndAction {
                // start the LoginActivity
                startSignUpActivity()
            }.startDelay = 50
        }

    }

    private fun resetViews() {
        // set all of the views to alpha 1
        peopleImage.alpha = 1f
        welcomeText.alpha = 1f
        descText.alpha = 1f
        getStartedButton.alpha = 1f

        peopleImage.translationY = 0f
        welcomeText.translationY = 0f
        descText.translationY = 0f
        getStartedButton.translationY = 0f

        val logo = findViewById<ImageView>(R.id.logo)
        logo.translationY = 0f

        animateViews()
    }

    private fun animateViews() {
        peopleImage.alpha = 0f
        welcomeText.alpha = 0f
        descText.alpha = 0f
        getStartedButton.alpha = 0f

        peopleImage.translationY = 200f
        welcomeText.translationY = 200f
        descText.translationY = 200f
        getStartedButton.translationY = 200f

        // animate all of the views to alpha 1 and slide up
        peopleImage.animate().alpha(1f).translationYBy(-200f).setDuration(2000).setInterpolator(android.view.animation.OvershootInterpolator(0.2f)).startDelay = 500
        welcomeText.animate().alpha(1f).translationYBy(-200f).setDuration(2000).setInterpolator(android.view.animation.OvershootInterpolator(0.2f)).startDelay = 1000
        descText.animate().alpha(1f).translationYBy(-200f).setDuration(2000).setInterpolator(android.view.animation.OvershootInterpolator(0.2f)).startDelay = 1300
        getStartedButton.animate().alpha(1f).translationYBy(-200f).setDuration(2000).setInterpolator(android.view.animation.OvershootInterpolator(0.2f)).startDelay = 1500
    }

    private fun startSignUpActivity()
    {
        // start the LoginActivity
        val intent = Intent(this, SignupOrLoginActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            findViewById(R.id.logo),
            "logo"
        )
        startActivity(intent, options.toBundle())
        // fade in the LoginActivity
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    override fun onResume() {
        super.onResume()
        resetViews()
    }
}
