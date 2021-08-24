package com.example.appyhour.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appyhour.R
import com.example.appyhour.bottleDatabase.Bottle
import com.example.appyhour.recipes.recipeList.Recipe
import com.example.appyhour.recipes.RecipeAdapter
import com.example.appyhour.recipes.RecipeApiStatus

@BindingAdapter("bottleImage")
fun ImageView.setBottleImage(item: Bottle?) {
    item?.let {
        setImageResource( when(item.bottleType) {
            "Vodka" -> R.drawable.vodka1
            "Tequila" -> R.drawable.tequila1
            "Rum" -> R.drawable.rum1
            "Gin" -> R.drawable.gin1
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

@BindingAdapter("convertIngredients")
fun TextView.setRecipeIngredients(item: Recipe?) {
    item?.let{
        text = item.ingredients.joinToString(", ")
    }
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}