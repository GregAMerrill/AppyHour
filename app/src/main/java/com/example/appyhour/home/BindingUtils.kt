package com.example.appyhour.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appyhour.R
import com.example.appyhour.database.Bottle
import com.example.appyhour.recipes.recipeList.Recipe
import com.example.appyhour.recipes.recipeList.RecipeAdapter
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

@BindingAdapter("recipeName")
fun TextView.setRecipeName(item: Recipe?) {
    item?.let{
        text = item.Name
    }
}

//Recipe Binding Adapters
@BindingAdapter("listRecipes")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Recipe>?) {
    val adapter = recyclerView.adapter as RecipeAdapter
    adapter.submitList(data)
}

@BindingAdapter("recipeIngredients")
fun TextView.setRecipeIngredients(item: Recipe?) {
    item?.let{
        text = item.Ingredients.toString()
    }
}

@BindingAdapter("recipeDirections")
fun TextView.setRecipeDirections(item: Recipe?) {
    item?.let{
        text = item.Directions
    }
}

@BindingAdapter("recipeApiStatus")
fun bindStatus(statusImageView: ImageView, status: RecipeApiStatus?) {
    when (status) {
        RecipeApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        RecipeApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        RecipeApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}