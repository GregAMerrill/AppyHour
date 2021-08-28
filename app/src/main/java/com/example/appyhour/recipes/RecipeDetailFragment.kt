package com.example.appyhour.recipes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.appyhour.databinding.RecipeDetailFragmentBinding
import com.example.appyhour.R

class RecipeDetailFragment : Fragment() {

    private lateinit var viewModel: RecipeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = RecipeDetailFragmentBinding.inflate(inflater)
        binding.recipe = RecipeDetailFragmentArgs.fromBundle(requireArguments()).recipe
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}