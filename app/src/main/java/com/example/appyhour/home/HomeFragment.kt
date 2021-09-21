package com.example.appyhour.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appyhour.R
import com.example.appyhour.bottleDatabase.BarDatabase
import com.example.appyhour.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = BarDatabase.getInstance(application).barDatabaseDao

        val viewModelFactory = HomeViewModel.Factory(dataSource, application)

        val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        homeViewModel.navigateToAddBottle.observe(viewLifecycleOwner, Observer { nav ->
            nav?.let {
                this.findNavController().navigate(R.id.action_homeFragment_to_addBottleFragment)
                homeViewModel.doneNavigatingToAddBottle()
            }
        })
        homeViewModel.navigateToRecipes.observe(viewLifecycleOwner, Observer { nav ->
            nav?.let {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRecipeFragment())
                homeViewModel.doneNavigatingToRecipes()
            }
        })

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.bar.layoutManager = manager

        val adapter = BarAdapter(BottleListener { bottleId ->
            homeViewModel.onBottleClicked(bottleId)
        })
        binding.bar.adapter = adapter

        homeViewModel.bottles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitBottleList(it)
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.help, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}