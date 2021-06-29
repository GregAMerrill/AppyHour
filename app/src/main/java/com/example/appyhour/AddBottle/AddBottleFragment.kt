package com.example.appyhour.AddBottle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.appyhour.R
import com.example.appyhour.databinding.FragmentAddBottleBinding
import com.example.appyhour.databinding.FragmentHomeBinding

class AddBottleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddBottleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_bottle, container, false)
        return binding.root
    }

}