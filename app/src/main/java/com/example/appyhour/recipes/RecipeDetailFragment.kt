package com.example.appyhour.recipes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.appyhour.R
import com.example.appyhour.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {

    lateinit var binding: FragmentRecipeDetailBinding

    private val viewModel :  RecipeDetailViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, RecipeDetailViewModel.Factory(activity.application))[RecipeDetailViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_detail,
            container,false)
        binding.recipe = RecipeDetailFragmentArgs.fromBundle(requireArguments()).recipe
        return binding.root
    }

}