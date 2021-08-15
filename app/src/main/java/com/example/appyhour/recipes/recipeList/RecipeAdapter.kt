package com.example.appyhour.recipes.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appyhour.databinding.ListItemRecipeBinding

class RecipeAdapter(val clickListener: RecipeListener) : ListAdapter<Recipe,
        RecipeAdapter.ViewHolder>(RecipeDiffCallback()) {

    //private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemRecipeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    class ViewHolder(val binding: ListItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe) {
            binding.recipe = item
            binding.executePendingBindings()
        }
    }
    class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipeItem = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(recipeItem)
        }
        holder.bind(recipeItem)
    }
}

class RecipeListener(val clickListener: (recipe: Recipe) -> Unit) {
    fun onClick(recipe: Recipe) = clickListener(recipe)
}

