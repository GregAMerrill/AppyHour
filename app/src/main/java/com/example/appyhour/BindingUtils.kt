package com.example.appyhour.home

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.appyhour.R
import com.example.appyhour.bottleDatabase.Bottle
import com.example.appyhour.recipes.recipeList.Recipe
import com.example.appyhour.recipes.RecipeAdapter

@BindingAdapter("bottleImage")
fun ImageView.setBottleImage(item: Bottle?) {
    item?.let {
        setImageResource( when(item.bottleType) {
            "Vodka" -> R.drawable.vodka1
            "Tequila" -> R.drawable.tequila1
            "Rum" -> R.drawable.rum1
            "Gin" -> R.drawable.gin1
            "Brandy" -> R.drawable.brandy1
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

@BindingAdapter("favoritePressed")
fun ImageButton.favoritePressed(item: Boolean) {
    item.let {
        if(it) setImageResource(R.drawable.full_heart_image)
        else setImageResource(R.drawable.empty_heart_image)
    }
}