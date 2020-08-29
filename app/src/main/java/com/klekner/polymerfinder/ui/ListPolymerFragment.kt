package com.klekner.polymerfinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.klekner.polymerfinder.R
import com.klekner.polymerfinder.data.Polymer
import com.klekner.polymerfinder.databinding.FragmentListPolymerBinding
import com.klekner.polymerfinder.db.PolymerDatabase
import com.klekner.polymerfinder.model.PolymerRepository
import com.klekner.polymerfinder.ui.viewmodel.Factory
import com.klekner.polymerfinder.ui.viewmodel.ListPolymerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass.
 */
class ListPolymerFragment : Fragment(), OnItemClickListener<Polymer> {

    private lateinit var viewModel: ListPolymerViewModel
    private lateinit var repository: PolymerRepository
    private val job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val db = PolymerDatabase.getDatabase(context!!.applicationContext, coroutineScope)
        repository = PolymerRepository(db.polymersDao)
        viewModel =
            ViewModelProviders.of(this,
                Factory(repository)
            )[ListPolymerViewModel::class.java]

        val adapter = PolymerListAdapter(this)
        val binding = FragmentListPolymerBinding.inflate(inflater, container, false)
        val recyclerView = binding.listPolymersRecyclerview


        recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this.context)
        }

        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.action_listPolymerFragment_to_addPolymerFragment)
        }

        subscribeUi(binding, adapter)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentListPolymerBinding, adapter: PolymerListAdapter) {
        viewModel.keeps.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result)
        })
    }

    override fun onClick(id: Polymer) {
        val action = ListPolymerFragmentDirections.actionListPolymerFragmentToPolymerInfoFragment(id.id)
        Navigation.findNavController(view!!).navigate(action)
    }

}
