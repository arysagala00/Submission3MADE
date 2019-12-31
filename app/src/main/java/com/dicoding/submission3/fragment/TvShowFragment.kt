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

import com.dicoding.submission3.R
import com.dicoding.submission3.activity.DetailShowActivity
import com.dicoding.submission3.adapter.ListShowAdapter
import com.dicoding.submission3.model.TvShow
import com.dicoding.submission3.viewmodel.ShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment(),LifecycleOwner {

    private lateinit var adapter: ListShowAdapter
    private lateinit var showViewModel: ShowViewModel

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

        adapter = ListShowAdapter()
        adapter.notifyDataSetChanged()

        rv_show.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv_show.adapter = adapter

        showViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ShowViewModel::class.java)

        handler.postDelayed({
            showViewModel.setShow()
            showLoading(true)
        },1000)

        showViewModel.getShow().observe(viewLifecycleOwner, Observer {showItems  ->
            if(showItems!=null){
                adapter.setData(showItems)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : ListShowAdapter.OnItemClickCallback{
            override fun onItemClicked(data: TvShow) {
                showSelectedShow(data)
            }
        })
    }

    private fun showLoading(state: Boolean){
        if(state){
            pbShow.visibility = View.VISIBLE
        }
        else{
            pbShow.visibility = View.GONE
        }
    }



    private fun showSelectedShow(show:TvShow){
        Toast.makeText(context,context?.getString(R.string.chosen)+" ${show.name}", Toast.LENGTH_SHORT).show()
        val showData = TvShow(
            show.photo,
            show.name,
            show.description,
            show.rating,
            show.year
        )

        val goToDetail = Intent(activity, DetailShowActivity::class.java)
        goToDetail.putExtra(DetailShowActivity.EXTRA_SHOW,showData)
        startActivity(goToDetail)
    }
}