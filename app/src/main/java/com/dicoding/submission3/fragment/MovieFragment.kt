package com.dicoding.submission3.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.dicoding.submission3.R
import com.dicoding.submission3.activity.DetailFilmActivity
import com.dicoding.submission3.adapter.ListMovieAdapter
import com.dicoding.submission3.model.Movie
import com.dicoding.submission3.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import org.json.JSONObject

class MovieFragment : Fragment(),LifecycleOwner {

    private lateinit var adapter: ListMovieAdapter
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler()

        adapter = ListMovieAdapter()
        adapter.notifyDataSetChanged()

        rv_list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv_list.adapter = adapter

        movieViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MovieViewModel::class.java)

        handler.postDelayed({
            movieViewModel.setMovie()
            showLoading(true)
        },1000)

        movieViewModel.getMovie().observe(viewLifecycleOwner, Observer {movieItems  ->
            if(movieItems!=null){
                adapter.setData(movieItems)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : ListMovieAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                showSelectedMovie(data)
            }
        })
    }

    private fun showLoading(state: Boolean){
        if(state){
            pbMovie.visibility = View.VISIBLE
        }
        else{
            pbMovie.visibility = View.GONE
        }
    }



    private fun showSelectedMovie(movie:Movie){
        Toast.makeText(context,context?.getString(R.string.chosen)+" ${movie.name}", Toast.LENGTH_SHORT).show()
        val movieData = Movie(
            movie.photo,
            movie.name,
            movie.description,
            movie.rating,
            movie.year
        )

        val goToDetail = Intent(activity, DetailFilmActivity::class.java)
        goToDetail.putExtra(DetailFilmActivity.EXTRA_MOVIES,movieData)
        startActivity(goToDetail)
    }
}