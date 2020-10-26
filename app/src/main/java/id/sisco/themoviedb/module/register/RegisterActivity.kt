package id.sisco.themoviedb.module.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ActivityRegisterBinding
import id.sisco.themoviedb.model.Status
import id.sisco.themoviedb.model.UserModel
import id.sisco.themoviedb.module.login.LoginActivity
import id.sisco.themoviedb.shared.getViewModel
import id.sisco.themoviedb.utils.CustomToast
import id.sisco.themoviedb.utils.Helpers
import kotlinx.android.synthetic.main.toolbar_primary.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private var isValidName = false
    private var isValidEmail = false
    private var isValidPassword = false
    private var isValidPassword2 = false
    private var isValidPhone = false
    private var isValidAddress = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)

        setToolbar()
        viewModel = getViewModel { RegisterViewModel() }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.click = ClickListener()

        viewModel.status.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    finishAffinity()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
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

        binding.name.addTextChangedListener {
            isName(it.toString())
        }

        binding.email.addTextChangedListener {
            isEmail(it.toString())
        }

        binding.password.addTextChangedListener {
            isPassword(it.toString())
        }

        binding.password2.addTextChangedListener {
            val password = binding.password.text.toString()
            isPassword2(password, it.toString())
        }

        binding.phone.addTextChangedListener {
            isPhone(it.toString())
        }

        binding.address.addTextChangedListener {
            isAddress(it.toString())
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tv_title_toolbar.text = resources.getString(R.string.register)
        toolbar.setNavigationOnClickListener { finish() }
    }

    inner class ClickListener {
        fun register(){
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val password2 = binding.password2.text.toString()
            val phone = binding.phone.text.toString()
            val address = binding.address.text.toString()

            isName(name)
            isEmail(email)
            isPassword(password)
            isPassword2(password, password2)
            isPhone(phone)
            isAddress(address)

            if (isValidName && isValidEmail && isValidPassword && isValidPassword2 && isValidPhone && isValidAddress) {
                binding.btnLogin.startAnimation()
                GlobalScope.launch(Dispatchers.Main) {
                    viewModel.register(UserModel(name, email, password, phone, address))
                }
            } else {
                isName(name)
                isEmail(email)
                isPassword(password)
                isPassword2(password, password2)
                isPhone(phone)
                isAddress(address)
            }
        }
    }

    private fun isName(name: String) {
        when {
            name.isEmpty() -> {
                binding.inputName.error = "Name must be fill"
                isValidName = false
            }
            else -> {
                binding.inputName.error = ""
                isValidName = true
            }
        }
    }

    private fun isEmail(email: String) {
        when {
            email.isEmpty() -> {
                binding.inputEmail.error = "Email must be fill"
                isValidEmail = false
            }
            !Helpers.isEmailValid(email) -> {
                binding.inputEmail.error = "Invalid email"
                isValidEmail = false
            }
            else -> {
                binding.inputEmail.error = ""
                isValidEmail = true
            }
        }
    }

    private fun isPassword(password: String) {
        when {
            password.isEmpty() -> {
                binding.inputPassword.error = "Password must be fill"
                isValidPassword = false
            }
            password.length < 6 -> {
                binding.inputPassword.error = "Password of at least 6 characters"
                isValidPassword = false
            }
            else -> {
                binding.inputPassword.error = ""
                isValidPassword = true
            }
        }
    }

    private fun isPassword2(password: String, password2: String) {
        when {
            password2.isEmpty() -> {
                binding.inputPassword2.error = "Confirm password must be fill"
                isValidPassword2 = false
            }
            password2.length < 6 -> {
                binding.inputPassword2.error = "Confirm password of at least 6 characters"
                isValidPassword2 = false
            }
            password != password2 -> {
                binding.inputPassword2.error = "Password and confirm password not matched"
                isValidPassword2 = false
            }
            else -> {
                binding.inputPassword2.error = ""
                isValidPassword2 = true
            }
        }
    }

    private fun isPhone(phone: String) {
        when {
            phone.isEmpty() -> {
                binding.inputPhone.error = "Phone Number must be fill"
                isValidPhone = false
            }
            else -> {
                binding.inputPhone.error = ""
                isValidPhone = true
            }
        }
    }

    private fun isAddress(address: String) {
        when {
            address.isEmpty() -> {
                binding.inputAddress.error = "Address must be fill"
                isValidAddress = false
            }
            else -> {
                binding.inputAddress.error = ""
                isValidAddress = true
            }
        }
    }
}