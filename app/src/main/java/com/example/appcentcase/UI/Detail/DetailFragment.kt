package com.example.appcentcase.UI.Detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentcase.R


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var textViewCity: TextView
    private val adapter=DetailViewAdapter()
    private lateinit var recycler: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCityWeather(args.woeid.toInt())
        textViewCity=view.findViewById(R.id.cityNameTextView)
        textViewCity.text=args.cityName+" \nWeather Forecast"
        recycler=view.findViewById(R.id.recycler)
        recycler.layoutManager= LinearLayoutManager(context)
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
        } } }