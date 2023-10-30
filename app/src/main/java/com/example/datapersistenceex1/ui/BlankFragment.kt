package com.example.datapersistenceex1.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.datapersistenceex1.databinding.FragmentBlankBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

        observeData()

        binding.lastFactButton.setOnClickListener {
            Toast.makeText(requireContext(),getFact(requireContext()),Toast.LENGTH_SHORT).show()
        }

    }

    private fun observeData() {

        factViewModel.isLoading.onEach { binding.progressBar.isVisible = it }
            .launchIn(lifecycleScope)

        factViewModel.result
            .onEach { fact ->
                if (fact != null){
                    binding.factLength.text = "Fact no. ${fact.length}"
                    binding.factText.text = fact.fact
                    saveFact(requireContext(), fact.fact)
                }
            }
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