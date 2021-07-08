package com.example.appyhour.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.appyhour.R
import com.example.appyhour.database.Bottle

@BindingAdapter("bottleImage")
fun ImageView.setBottleImage(item: Bottle?) {
    item?.let {
        setImageResource( when(item.bottleType) {
            "Vodka" -> R.drawable.vodka1
            else -> R.drawable.whiskey1
        })
    }
}

@BindingAdapter("bottleString")
fun TextView.setBottleString(item: Bottle?) {
    item?.let {
        text = item.bottleName
    }
}