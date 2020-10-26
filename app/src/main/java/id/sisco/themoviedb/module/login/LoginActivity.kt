package id.sisco.themoviedb.module.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ActivityLoginBinding
import id.sisco.themoviedb.model.Status
import id.sisco.themoviedb.module.main.MainActivity
import id.sisco.themoviedb.module.register.RegisterActivity
import id.sisco.themoviedb.shared.getViewModel
import id.sisco.themoviedb.utils.CustomToast
import id.sisco.themoviedb.utils.Helpers
import id.sisco.themoviedb.utils.SPManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var isValidEmail = false
    private var isValidPassword = false
    private val pref by lazy { SPManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)


        viewModel = getViewModel { LoginViewModel(pref) }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.click = ClickListener()

        viewModel.status.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    goToMain()
                }
                else -> {}
            }
        })

        viewModel.msg.observe(this, Observer {
            if (viewModel.status.value?.status == Status.ERROR ){
                CustomToast.failureToast(this, it, CustomToast.GRAVITY_BOTTOM)
                binding.btnLogin.revertAnimation()
            }
        })

        binding.email.addTextChangedListener {
            isEmail(it.toString())
        }

        binding.password.addTextChangedListener {
            isPassword(it.toString())
        }
    }

    inner class ClickListener {
        fun login(){
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (isValidEmail && isValidPassword) {
                binding.btnLogin.startAnimation()
                GlobalScope.launch(Dispatchers.Main) {
                    viewModel.login(email, password)
                }
            } else {
                isEmail(email)
                isPassword(password)
            }

        }

        fun register(){
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }


    private fun goToMain() {
        finishAffinity()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    private fun isEmail(email: String) {
        when {
            email.isEmpty() -> {
                binding.textInputLayout.error = "Email must be fill"
                isValidEmail = false
            }
            !Helpers.isEmailValid(email) -> {
                binding.textInputLayout.error = "Invalid email"
                isValidEmail = false
            }
            else -> {
                binding.textInputLayout.error = ""
                isValidEmail = true
            }
        }
    }

    private fun isPassword(password: String) {
        when {
            password.isEmpty() -> {
                binding.textInputLayout2.error = "Password must be fill"
                isValidPassword = false
            }
            password.length < 6 -> {
                binding.textInputLayout2.error = "Password of at least 6 characters"
                isValidPassword = false
            }
            else -> {
                binding.textInputLayout2.error = ""
                isValidPassword = true
            }
        }
    }
}