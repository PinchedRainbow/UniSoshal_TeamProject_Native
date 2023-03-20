package com.faheemsaleem.unisoshal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // hide the status bar
       // window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // set image alpha to 0
        findViewById<View>(R.id.imageView).alpha = 0f
        // animate the image alpha to 1 and scale to 1.1 in 2 seconds and slide up
        findViewById<View>(R.id.imageView).animate().alpha(1f).scaleX(1.1f).scaleY(1.1f).setDuration(2000).translationY(-20f).start()

        // wait for 3 seconds with a coroutine
        // then start the MainActivity
        val handler = Handler()
        handler.postDelayed({
            // your code to run after 2 second
            startMainActivity()
        }, 2000)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            findViewById(R.id.imageView),
            "logo"
        )
        val activityName = "SplashActivity"
        intent.putExtra("activityName", activityName)
        startActivity(intent, options.toBundle())
        overridePendingTransition(com.google.android.material.R.anim.abc_shrink_fade_out_from_bottom,
            com.google.android.material.R.anim.abc_grow_fade_in_from_bottom);
        finish()
    }
}
