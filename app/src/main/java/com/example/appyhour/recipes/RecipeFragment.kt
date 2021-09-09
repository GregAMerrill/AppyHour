package com.example.appyhour.recipes

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
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
        ViewModelProvider(activity, RecipeViewModel.Factory(activity.application, this))[RecipeViewModel::class.java]
    }

    private lateinit var binding: FragmentRecipeBinding
    private var viewModelAdapter: RecipeAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(viewModel.recipeFilter.value) {
            ListType.FULL -> binding.recipeTitle.text = getString(R.string.all_recipes_string)
            ListType.SAVED ->binding.recipeTitle.text = getString(R.string.my_recipes_string)
        }
        viewModel.recipeList.observe(viewLifecycleOwner, {
            filterRecipeList(viewModel.recipeFilter.value!!)
        })
        viewModel.recipeFilter.observe(viewLifecycleOwner, { listType ->
            filterRecipeList(listType)
        })
        viewModel.navToRecipeDetail.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(RecipeFragmentDirections.actionRecipeFragmentToRecipeDetailFragment(it))
                viewModel.doneNavigatingToDetail()
            }
        })
    }

    private fun filterRecipeList(listType: ListType) {
        viewModel.recipeList.value?.apply{
            val myRecipes = this.filter { recipe -> recipe.isSaved }.sortedBy { it.name }
            binding.recipesFrameLayout.background =
                getDrawable(requireContext(), R.drawable.recipe_scroll_box)
            if(listType == ListType.SAVED) viewModelAdapter?.recipes = myRecipes
            else viewModelAdapter?.recipes = this.sortedBy { it.name }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe,
            container,false)

        viewModelAdapter = RecipeAdapter(RecipeListener {
            viewModel.navigateToRecipeDetail(it)
        })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recipes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
            addItemDecoration(VerticalSpaceItemDecoration(12))
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
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
        outRect.top = verticalSpaceHeight
    }
}