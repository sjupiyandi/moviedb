package id.sisco.themoviedb.module.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ItemGenreBinding
import id.sisco.themoviedb.model.MovieDetailModel
import kotlinx.android.extensions.LayoutContainer

class GenreAdapter : RecyclerView.Adapter<GenreViewHolder>() {

    private val list = ArrayList<MovieDetailModel.Genre>()

    fun setData(items: ArrayList<MovieDetailModel.Genre>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding: ItemGenreBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_genre,
            parent,
            false
        )
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item: MovieDetailModel.Genre = list[position]
        holder.bind(item)
    }
}

class GenreViewHolder(val binding: ItemGenreBinding) :
    RecyclerView.ViewHolder(binding.root),
    LayoutContainer {

    override val containerView: View?
        get() = itemView


    fun bind(item: MovieDetailModel.Genre) {
        this.binding.viewmodel = item
        binding.executePendingBindings()

    }


}