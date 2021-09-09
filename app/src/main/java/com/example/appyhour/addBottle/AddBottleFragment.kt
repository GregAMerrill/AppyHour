package com.example.appyhour.addBottle

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appyhour.R
import com.example.appyhour.bottleDatabase.BarDatabase
import com.example.appyhour.databinding.FragmentAddBottleBinding
import com.google.android.material.snackbar.Snackbar

private lateinit var bottleType: String
private var bottleName = ""

class AddBottleFragment : Fragment() {

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            val item = parent.getItemAtPosition(pos).toString()
            bottleType = item
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            bottleType = "Vodka"
        }
    }

    fun hideSoftKeyboard() {
        val activity = this.activity
        val inputMethodManager: InputMethodManager = activity?.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentAddBottleBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_bottle, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = BarDatabase.getInstance(application).barDatabaseDao
        val viewModelFactory = AddBottleViewModel.Factory(dataSource)

        val addBottleViewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddBottleViewModel::class.java)

        binding.addBottleViewModel = addBottleViewModel

        val spinner: Spinner = binding.addBottleType
        ArrayAdapter.createFromResource(
            this.requireContext(), R.array.bottle_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = SpinnerActivity()

        binding.addButton.setOnClickListener {
            bottleName = binding.addBottleName.text.toString()
            if(bottleName.isNotEmpty()) {
                addBottleViewModel.onSubmitBottle(bottleName, bottleType)
                this.findNavController().navigate(AddBottleFragmentDirections.actionAddBottleFragmentToHomeFragment())
            } else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.no_bottle_name),
                    Snackbar.LENGTH_SHORT
                ) // How long to display the message.
                .show()
            }
        }
        binding.addBottleLayout.setOnClickListener {
            hideSoftKeyboard()
        }
        return binding.root
    }
}

