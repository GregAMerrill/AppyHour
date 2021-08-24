package com.example.appyhour.recipes

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appyhour.R
import com.example.appyhour.databinding.FragmentRecipeBinding
import com.example.appyhour.databinding.ListItemRecipeBinding
import com.example.appyhour.recipes.recipeList.Recipe

class RecipeFragment : Fragment() {

    private val viewModel :  RecipeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, RecipeViewModel.Factory(activity.application)).get(RecipeViewModel::class.java)
    }

    private var viewModelAdapter: RecipeAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipeList.observe(viewLifecycleOwner, { recipes ->
            recipes?.apply {
                viewModelAdapter?.recipes = recipes
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
            val binding: FragmentRecipeBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_recipe,
                container,
                false)


        viewModelAdapter = RecipeAdapter(RecipeListener {
            this.findNavController().navigate(R.id.action_recipeFragment_to_homeFragment)
            Log.i("Recipe Clicked: ", it.toString())
        })

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.recipes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
            addItemDecoration(VerticalSpaceItemDecoration(16))
        }
        return binding.root
    }
}

class RecipeListener(val clickListener: (Recipe) -> Unit) {
    fun onClick(recipe: Recipe) = clickListener(recipe)
}

class RecipeAdapter(val clickListener: RecipeListener) : RecyclerView.Adapter<RecipeViewHolder>() {

    var recipes: List<Recipe> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val dataBinding: ListItemRecipeBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                RecipeViewHolder.LAYOUT,
                parent,
                false)
        Log.i("CreateViewHolder","CREATEVIEWHOLDER")
        return RecipeViewHolder(dataBinding)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.also {
            it.recipe = recipes[position]
            it.clickListener = clickListener
        }
    }

}

class RecipeViewHolder(val binding: ListItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_recipe
    }
}

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.bottom = verticalSpaceHeight
    }
}