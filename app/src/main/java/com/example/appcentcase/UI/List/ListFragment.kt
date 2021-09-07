package com.example.appcentcase.UI.List


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentcase.Listeners.ICityClickListener
import com.example.appcentcase.Model.weatherLocationItem
import com.example.appcentcase.R
import com.example.appcentcase.databinding.FragmentListBinding


class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var _binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private var adapter=ListViewAdapter()
    private lateinit var recycler:RecyclerView
    private lateinit var editTextInput: EditText
    private lateinit var buttonSearch: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextInput = view.findViewById(R.id.input)
        buttonSearch = view.findViewById(R.id.button)

       // val bundle = arguments
        //val message = bundle?.getString("message")
       // Log.v("Fragment","$message")

        viewModel.getCitiesByLocation("36.96,-122.02")

        buttonSearch.setOnClickListener {

            val searchKey = editTextInput.text.toString()
            viewModel.getCitiesBySearch(searchKey)
        }

        recycler=view.findViewById(R.id.recycler)
        recycler.layoutManager=LinearLayoutManager(context)
        recycler.adapter=adapter
        adapter.setMovieOnClickListener(object : ICityClickListener {
            override fun onClick(city: weatherLocationItem) {

               val action=ListFragmentDirections.actionListFragmentToDetailFragment(
                city.woeid.toString()
               )
                findNavController().navigate(action)
            }
        })
        observeViewModel()

    }
    private fun observeViewModel() {
        viewModel.showListLiveData.observe(viewLifecycleOwner) {
            adapter.apply {
                list = it
                notifyDataSetChanged()
            }
        }
        viewModel.errorStateLiveData.observe(this) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


}