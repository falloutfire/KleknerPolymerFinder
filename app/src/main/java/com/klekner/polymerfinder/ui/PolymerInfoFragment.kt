package com.klekner.polymerfinder.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.klekner.polymerfinder.data.Polymer
import com.klekner.polymerfinder.databinding.FragmentPolymerInfoBinding
import com.klekner.polymerfinder.db.PolymerDatabase
import com.klekner.polymerfinder.model.PolymerRepository
import com.klekner.polymerfinder.ui.viewmodel.InfoFactory
import com.klekner.polymerfinder.ui.viewmodel.PolymerInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass.
 */
class PolymerInfoFragment : Fragment(), OnItemClickListener<Polymer> {

    private lateinit var binding: FragmentPolymerInfoBinding
    private lateinit var viewModel: PolymerInfoViewModel
    private lateinit var repository: PolymerRepository
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val id = arguments?.let { PolymerInfoFragmentArgs.fromBundle(it).polymerId }

        val db = PolymerDatabase.getDatabase(context!!.applicationContext, coroutineScope)
        repository = PolymerRepository(db.polymersDao)
        viewModel =
            ViewModelProviders.of(this,
                InfoFactory(repository)
            )[PolymerInfoViewModel::class.java]

        binding = FragmentPolymerInfoBinding.inflate(inflater, container, false)

        val adapter = PolymerListAdapter(this)
        val recyclerView = binding.listPolymersRecyclerview

        recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this.context)
        }

        id?.let {
            viewModel.getPolymer(it)
            viewModel.getBlends(it)
        }

        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: PolymerListAdapter) {

        viewModel.blends.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result)
        })
        viewModel.polymer.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.item = it
            }
        })

    }

    override fun onClick(id: Polymer) {
        Log.e("test", "test")
    }

}
