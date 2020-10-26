package id.sisco.themoviedb.module.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ActivityMainBinding
import id.sisco.themoviedb.model.Status
import id.sisco.themoviedb.module.profile.ProfileActivity
import id.sisco.themoviedb.shared.getViewModel
import kotlinx.android.synthetic.main.custom_toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        setToolbar()
        viewModel = getViewModel { MainViewModel() }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter()
        binding.rvMovie.adapter = adapter
        adapter.notifyDataSetChanged()

        getData()

    }

    override fun onResume() {
        super.onResume()
        viewModel.setMovie()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tv_title_toolbar.text = resources.getString(R.string.popular)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.profile -> goToProfile()
        }
        return true
    }

    private fun goToProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    private fun getData() {
        viewModel.status.observe(this, Observer { result ->
            when (result.status){
                Status.LOADING -> {
                    binding.emptyMovie.viewEmpty.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyMovie.viewEmpty.visibility = View.GONE
                    binding.rvMovie.visibility = View.VISIBLE
                }
                Status.EMPTY -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyMovie.viewEmpty.visibility = View.VISIBLE
                    binding.rvMovie.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvMovie.visibility = View.GONE
                    binding.emptyMovie.viewEmpty.visibility = View.VISIBLE
                }
                else -> {}
            }
        })

        viewModel.msg.observe(this, Observer {
            if (viewModel.status.value?.status == Status.ERROR || viewModel.status.value?.status == Status.EMPTY){
                binding.emptyMovie.messageStatus.text = it
            }
        })

        viewModel.listMovie.observe(this, Observer {
            when (viewModel.status.value?.status) {
                Status.SUCCESS -> {
                    adapter.setData(it)
                }
                else -> {
                }
            }
        })
    }
}