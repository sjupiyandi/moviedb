package id.sisco.themoviedb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.sisco.themoviedb.module.login.LoginActivity
import id.sisco.themoviedb.module.main.MainActivity
import id.sisco.themoviedb.utils.SPManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            if (!SPManager(this).getSPSudahLogin()) {
                goToLogin()
            } else {
                goToMain()
            }
        }, 2000)

    }

    private fun goToLogin() {
        finishAffinity()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToMain() {
        finishAffinity()
        startActivity(Intent(this, MainActivity::class.java))
    }
}