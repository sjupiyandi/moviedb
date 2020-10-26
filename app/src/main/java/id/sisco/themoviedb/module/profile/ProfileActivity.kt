package id.sisco.themoviedb.module.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ActivityProfileBinding
import id.sisco.themoviedb.model.Status
import id.sisco.themoviedb.module.login.LoginActivity
import id.sisco.themoviedb.shared.getViewModel
import id.sisco.themoviedb.utils.CustomToast
import id.sisco.themoviedb.utils.SPManager
import kotlinx.android.synthetic.main.toolbar_primary.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private val pref by lazy { SPManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setToolbar()
        viewModel = getViewModel { ProfileViewModel() }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.click = ClickListener()

        viewModel.setUser(pref.getSPUserId().toString())

        viewModel.status.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layout.visibility = View.VISIBLE
                }
                else -> {}
            }
        })

        viewModel.msg.observe(this, Observer {
            if (viewModel.status.value?.status == Status.ERROR ){
                CustomToast.failureToast(this, it, CustomToast.GRAVITY_BOTTOM)
                finish()
            }
        })
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        tv_title_toolbar.text = resources.getString(R.string.profile)
        toolbar.setNavigationOnClickListener { finish() }
    }

    inner class ClickListener {
        fun logout(){
            pref.saveSPSudahLogin(false)
            finishAffinity()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
        }
    }
}