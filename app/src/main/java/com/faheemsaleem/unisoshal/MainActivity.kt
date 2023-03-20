package com.faheemsaleem.unisoshal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val peopleImage = findViewById<ImageView>(R.id.imageView2)
        val welcomeText = findViewById<TextView>(R.id.textView)
        val descText = findViewById<TextView>(R.id.textView2)
        val getStartedButton = findViewById<Button>(R.id.button)

        // set all of the views to alpha 0
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

        // give the button an onClickListener
        getStartedButton.setOnClickListener {
            // start the LoginActivity
            //startActivity(Intent(this, LoginActivity::class.java))
        }


    }
}
