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
import com.dicoding.submission3.activity.DetailShowActivity
import com.dicoding.submission3.adapter.ListShowAdapter
import com.dicoding.submission3.model.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private val list = ArrayList<TvShow>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler()

        rv_show.visibility = View.GONE

        handler.postDelayed({
            getData()
        },3000)
    }

    fun getData(){
        rv_show.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        val listShowAdapter = ListShowAdapter(list)
        rv_show.adapter = listShowAdapter

        rv_show.setHasFixedSize(true)

        val url = "https://api.themoviedb.org/3/discover/tv?api_key=07fc7d411ee3ae0037267e38d68e091c&language=en-US"

        val request = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { res ->
                var it = JSONObject(res)
                Log.d("JSON",res)
                for(i in 0 until it.getJSONArray("results").length()){
                    val result = it.getJSONArray("results")[i] as JSONObject
                    val name = result.getString("name")
                    val description = result.getString("overview")
                    val rating = result.getString("vote_average")
                    val photo = "https://image.tmdb.org/t/p/w342"+result.getString("poster_path")
                    val year = result.getString("first_air_date")

                    list.add(TvShow(photo,name,description,rating,year))
                }

                listShowAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {

            })

        val queue = Volley.newRequestQueue(activity?.applicationContext)
        queue.add(request)

        pbShow.visibility = View.GONE
        rv_show.visibility = View.VISIBLE

        listShowAdapter.setOnItemClickCallback(object : ListShowAdapter.OnItemClickCallback{
            override fun OnItemClicked(data: TvShow) {
                showSelectedShow(data)
            }
        })
    }

    private fun showSelectedShow(data:TvShow){
        Toast.makeText(context,context?.getString(R.string.chosen)+" ${data.name}", Toast.LENGTH_SHORT).show()
        val showData = TvShow(
            data.photo,
            data.name,
            data.description,
            data.rating,
            data.year
        )

        val goToDetail = Intent(activity,DetailShowActivity::class.java)
        goToDetail.putExtra(DetailShowActivity.EXTRA_SHOW,showData)
        startActivity(goToDetail)
    }
}
