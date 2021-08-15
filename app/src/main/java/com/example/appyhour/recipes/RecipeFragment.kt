package com.example.appyhour.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appyhour.recipes.recipeList.VerticalSpaceItemDecoration
import com.example.appyhour.databinding.FragmentRecipeBinding
import com.example.appyhour.recipes.recipeList.RecipeAdapter
import com.example.appyhour.recipes.recipeList.RecipeListener

class RecipeFragment : Fragment() {

    private val viewModel :  RecipeViewModel by lazy {
        ViewModelProvider(this).get(RecipeViewModel::class.java)
    }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            val binding = FragmentRecipeBinding.inflate(inflater)

            binding.lifecycleOwner = this

            binding.viewModel = viewModel

            binding.recipes.adapter = RecipeAdapter(RecipeListener{
                viewModel.displayRecipeDetails(it)
            })

            binding.recipes.addItemDecoration(VerticalSpaceItemDecoration(16))

            viewModel.navigateToSelectedRecipe.observe(viewLifecycleOwner, Observer {
                if (null != it) {
                    this.findNavController()
                        .navigate(RecipeFragmentDirections.actionRecipeFragmentToHomeFragment())
                    viewModel.displayRecipeDetailsComplete()
                }
            } )
            return binding.root
        }
}
