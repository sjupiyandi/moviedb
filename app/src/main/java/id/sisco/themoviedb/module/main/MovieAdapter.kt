package id.sisco.themoviedb.module.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ItemMovieBinding
import id.sisco.themoviedb.model.MovieModel
import id.sisco.themoviedb.module.detail.DetailActivity
import kotlinx.android.extensions.LayoutContainer

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private val list = ArrayList<MovieModel.Result>()

    fun setData(items: ArrayList<MovieModel.Result>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item: MovieModel.Result = list[position]
        holder.bind(item)
    }
}

class MovieViewHolder(val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root),
    LayoutContainer {

    override val containerView: View?
        get() = itemView


    fun bind(item: MovieModel.Result) {
        this.binding.viewmodel = item
        binding.executePendingBindings()

        containerView?.setOnClickListener {
            it.context.startActivity(
                Intent(it.context, DetailActivity::class.java)
                    .putExtra("id", item.id.toString()))
        }

    }


}