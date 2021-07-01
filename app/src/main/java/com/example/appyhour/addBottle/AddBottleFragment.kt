package com.example.appyhour.addBottle

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appyhour.R
import com.example.appyhour.database.BarDatabase
import com.example.appyhour.databinding.FragmentAddBottleBinding

class AddBottleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentAddBottleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_bottle, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = BarDatabase.getInstance(application).barDatabaseDao
        val viewModelFactory = AddBottleViewModelFactory(dataSource)

        val addBottleViewModel = ViewModelProvider(this, viewModelFactory).get(AddBottleViewModel::class.java)

        binding.addBottleViewModel = addBottleViewModel

        val spinner: Spinner = binding.addBottleType
        ArrayAdapter.createFromResource(
            this.requireContext(), R.array.bottle_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        addBottleViewModel.navigateToHome.observe(viewLifecycleOwner, Observer{
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    AddBottleFragmentDirections.actionAddBottleFragmentToHomeFragment())
                addBottleViewModel.doneNavigating()
            }
        })

        return binding.root
    }

}

class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val item = parent.getItemAtPosition(pos)
        
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}