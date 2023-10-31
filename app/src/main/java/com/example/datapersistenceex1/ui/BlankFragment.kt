package com.example.datapersistenceex1.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.datapersistenceex1.FactState
import com.example.datapersistenceex1.databinding.FragmentBlankBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class BlankFragment : Fragment() {

    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    private lateinit var factViewModel: FactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factViewModel = ViewModelProvider(this).get(FactViewModel::class.java)

        binding.networkButton.setOnClickListener {
            factViewModel.getFacts()
        }

        lifecycleScope.launch{
            Log.d("COROUTINE","Observe Data Called.")
            observeData()
        }

        binding.lastFactButton.setOnClickListener {
            Toast.makeText(requireContext(),getFact(requireContext()),Toast.LENGTH_SHORT).show()
        }

    }

    private suspend fun observeData() {

        factViewModel.state.collect{state ->
            binding.progressBar.isVisible = false
            when(state){
                FactState.IsLoading -> binding.progressBar.isVisible = true
                is FactState.Result -> {
                    binding.factLength.text = "Fact no. ${state.fact.length}"
                    binding.factText.text = state.fact.fact
                    saveFact(requireContext(), state.fact.fact)
                }
            }
        }

//        factViewModel.isLoading.onEach { binding.progressBar.isVisible = it }.collect()
//
//        factViewModel.state
//            .onEach { fact ->
//                if (fact != null){
//                    binding.factLength.text = "Fact no. ${fact.length}"
//                    binding.factText.text = fact.fact
//                    saveFact(requireContext(), fact.fact)
//                }
//            }.collect()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveFact(context: Context, fact: String?){
        val sharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()){
            putString("LastFact",fact)
            apply()
        }
    }

    private fun getFact(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        return sharedPreferences.getString("LastFact","No fact available")
    }


}