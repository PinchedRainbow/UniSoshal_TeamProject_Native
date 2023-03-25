package com.faheemsaleem.unisoshal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class SignupOrLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_or_login)
        val emailInput = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.email)
        val passwordInput = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.password)
        val signupButton = findViewById<Button>(R.id.button2)

        signupButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            if (email == "" || password == "")
            {
                Toast.makeText(this, "Please enter an email and password", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val emailIsValid = validateEmail(email)
                val passwordIsValid = validatePassword(password)
                if (!emailIsValid) emailInput.error = "Please enter a valid email ensuring it contains @ and ."
                if (!passwordIsValid) passwordInput.error = "Please enter a valid password ensuring it is at least 8 characters long"

                if (emailIsValid && passwordIsValid)
                 {
                      // start the LoginActivity
                    val intent = Intent(this, BottomNavBarActivity::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("password", password)
                     val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                         this,
                         findViewById(R.id.imageView3),
                         "logo"
                     )
                      startActivity(intent, options.toBundle())
                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                 }

            }
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun validateEmail(email: String): Boolean{
        // check for @, and .
        val regex = Regex("[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+")
        return regex.matches(email)
    }

    private fun validatePassword(password: String): Boolean{
        // check for length
        val regex = Regex("[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+")
        return password.length >= 8
    }
}
