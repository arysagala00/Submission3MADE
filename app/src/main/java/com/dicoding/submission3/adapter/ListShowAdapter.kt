package com.dicoding.submission3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission3.R
import com.dicoding.submission3.model.TvShow
import kotlinx.android.synthetic.main.item_row_show.view.*

class ListShowAdapter() : RecyclerView.Adapter<ListShowAdapter.ListViewHolder>() {

    private val listShow = ArrayList<TvShow>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items:ArrayList<TvShow>){
        listShow.clear()
        listShow.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListShowAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_show,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListShowAdapter.ListViewHolder, position: Int) {
        holder.bind(listShow[position])
    }

    override fun getItemCount(): Int = listShow.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(show:TvShow){
            with(itemView){
                var year = show.year
                Glide.with(this).load(show.photo).into(img_tv)
                title_tv.text = show.name
                year_tv.text = year.substring(0,4)
                description_tv.text = show.description

                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(show)}
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data:TvShow)
    }
}