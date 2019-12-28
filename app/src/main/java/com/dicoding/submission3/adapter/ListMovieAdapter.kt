package com.dicoding.submission3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission3.R
import com.dicoding.submission3.model.Movie
import kotlinx.android.synthetic.main.item_row_movie.view.*

class ListMovieAdapter(private val listMovie:ArrayList<Movie>) : RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMovieAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListMovieAdapter.ListViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int = listMovie.size

    inner class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(movie:Movie){
            with(itemView){
                var year = movie.year
                Glide.with(this).load(movie.photo).into(img_photo)
                txt_name.text = movie.name
                txt_year.text = year.substring(0,4)
                txt_description.text = movie.description

                itemView.setOnClickListener{onItemClickCallback?.OnItemClicked(movie)}
            }
        }
    }

    interface OnItemClickCallback {
        fun OnItemClicked(data:Movie)
    }
}