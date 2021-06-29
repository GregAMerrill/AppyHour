package com.example.appyhour.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.appyhour.R
import com.example.appyhour.database.Bottle

@BindingAdapter("bottleImage")
fun ImageView.setSleepImage(item: Bottle?) {
    item?.let {
        setImageResource(R.drawable.whiskey1)
    }
}