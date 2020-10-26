package id.sisco.themoviedb.module.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ActivityDetailBinding
import id.sisco.themoviedb.model.Status
import id.sisco.themoviedb.shared.getViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: GenreAdapter
    private lateinit var adapterCredit: CreditAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)

        viewModel = getViewModel { DetailViewModel() }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        adapter = GenreAdapter()
        binding.rvGenre.adapter = adapter
        adapter.notifyDataSetChanged()

        binding.rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        adapterCredit = CreditAdapter()
        binding.rvCast.adapter = adapterCredit
        adapterCredit.notifyDataSetChanged()

        viewModel.setDetailMovie(intent.extras?.getString("id").toString())
        getData()

        binding.back.setOnClickListener { finish() }

    }

    private fun getData() {
        viewModel.status.observe(this, Observer { result ->
            when (result.status){
                Status.LOADING -> {
                    binding.emptyMovie.viewEmpty.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layout.visibility = View.GONE
                    binding.layout2.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyMovie.viewEmpty.visibility = View.GONE
                    binding.layout.visibility = View.VISIBLE
                    binding.layout2.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyMovie.viewEmpty.visibility = View.VISIBLE
                    binding.layout.visibility = View.GONE
                    binding.layout2.visibility = View.GONE
                }
                else -> {}
            }
        })

        viewModel.msg.observe(this, Observer {
            if (viewModel.status.value?.status == Status.ERROR){
                binding.emptyMovie.messageStatus.text = it
            }
        })

        viewModel.listGenre.observe(this, Observer {
            when (viewModel.status.value?.status) {
                Status.SUCCESS -> {
                    adapter.setData(it)
                }
                else -> {
                }
            }
        })

        viewModel.detailVideo.observe(this, Observer {video ->
            binding.play.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=${video.key}")
                    )
                )

            }
        })

        viewModel.detailCast.observe(this, Observer {
            when (viewModel.status.value?.status) {
                Status.SUCCESS -> {
                    adapterCredit.setData(it)
                }
                else -> {
                }
            }
        })

    }
}