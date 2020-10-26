package id.sisco.themoviedb.module.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.sisco.themoviedb.R
import id.sisco.themoviedb.databinding.ItemCreditBinding
import id.sisco.themoviedb.model.MovieCreditModel
import kotlinx.android.extensions.LayoutContainer

class CreditAdapter : RecyclerView.Adapter<CreditViewHolder>() {

    private val list = ArrayList<MovieCreditModel.Cast>()

    fun setData(items: ArrayList<MovieCreditModel.Cast>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val binding: ItemCreditBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_credit,
            parent,
            false
        )
        return CreditViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val item: MovieCreditModel.Cast = list[position]
        holder.bind(item)
    }
}

class CreditViewHolder(val binding: ItemCreditBinding) :
    RecyclerView.ViewHolder(binding.root),
    LayoutContainer {

    override val containerView: View?
        get() = itemView


    fun bind(item: MovieCreditModel.Cast) {
        this.binding.viewmodel = item
        binding.executePendingBindings()

    }


}