package com.dicoding.submission3.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.submission3.R
import com.dicoding.submission3.model.Movie
import kotlinx.android.synthetic.main.activity_detail_film.*

class DetailFilmActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_MOVIES = "extra_movies"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val result = intent.getParcelableExtra(EXTRA_MOVIES) as Movie
        val year = result.year

        Glide.with(this).load(result.photo).into(img_detail)
        data_name.text = result.name
        data_rating.text = result.rating
        data_year.text = year.substring(0,4)
        data_description.text = result.description
    }
}
