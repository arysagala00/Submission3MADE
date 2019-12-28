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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.dicoding.submission3.R
import com.dicoding.submission3.activity.DetailFilmActivity
import com.dicoding.submission3.adapter.ListMovieAdapter
import com.dicoding.submission3.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.*
import org.json.JSONObject

class MovieFragment : Fragment() {

    private val list = ArrayList<Movie>()

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

        rv_list.visibility = View.GONE

        handler.postDelayed({
            getData()
        },3000)
    }

    private fun getData(){
        rv_list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val listMovieAdapter = ListMovieAdapter(list)
        rv_list.adapter = listMovieAdapter

        rv_list.setHasFixedSize(true)

        val url = "https://api.themoviedb.org/3/discover/movie?api_key=07fc7d411ee3ae0037267e38d68e091c&language=en-US"

        val request = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { res ->
                var it = JSONObject(res)
                Log.d("JSON",res)
                for(i in 0 until it.getJSONArray("results").length()){
                    val result = it.getJSONArray("results")[i] as JSONObject
                    val name = result.getString("title")
                    val description = result.getString("overview")
                    val rating = result.getString("vote_average")
                    val photo = "https://image.tmdb.org/t/p/w342"+result.getString("poster_path")
                    val year = result.getString("release_date")

                    list.add(Movie(photo,name,description,rating,year))
                }

                listMovieAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {

            })

        val queue = Volley.newRequestQueue(activity?.applicationContext)
        queue.add(request)

        rv_list.visibility = View.VISIBLE
        pbMovie.visibility = View.GONE

        listMovieAdapter.setOnItemClickCallback(object : ListMovieAdapter.OnItemClickCallback{
            override fun OnItemClicked(data: Movie) {
                showSelectedMovie(data)
            }
        })
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
