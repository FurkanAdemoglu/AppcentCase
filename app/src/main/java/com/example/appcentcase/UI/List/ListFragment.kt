package com.example.appcentcase.UI.List


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentcase.R
import com.example.appcentcase.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private lateinit var _binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private var adapter=ListViewAdapter()
    private lateinit var recycler:RecyclerView
    //val bundle = arguments
    //val message = bundle?.getString("message")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("Fragment","BUrada")
        viewModel.getCitiesByLocation("36.96,-122.02")

        recycler=view.findViewById(R.id.recycler)
        recycler.layoutManager=LinearLayoutManager(context)
        recycler.adapter=adapter
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