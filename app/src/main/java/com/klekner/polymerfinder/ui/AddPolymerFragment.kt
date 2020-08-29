package com.klekner.polymerfinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.klekner.polymerfinder.data.Polymer
import com.klekner.polymerfinder.databinding.FragmentAddPolymerBinding
import com.klekner.polymerfinder.db.PolymerDatabase
import com.klekner.polymerfinder.model.PolymerRepository
import com.klekner.polymerfinder.ui.viewmodel.AddPolymerFactory
import com.klekner.polymerfinder.ui.viewmodel.AddPolymerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass.
 */
class AddPolymerFragment : Fragment() {

    private lateinit var binding: FragmentAddPolymerBinding
    private lateinit var viewModel: AddPolymerViewModel
    private lateinit var repository: PolymerRepository
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        binding = FragmentAddPolymerBinding.inflate(inflater, container, false)
        val db = PolymerDatabase.getDatabase(context!!.applicationContext, coroutineScope)
        repository = PolymerRepository(db.polymersDao)

        binding.addItem = Polymer()
        viewModel =
            ViewModelProviders.of(this,
                AddPolymerFactory(repository)
            )[AddPolymerViewModel::class.java]

        binding.floatingActionButton.setOnClickListener {
            val item = Polymer(binding.fullnameEditText.text.toString(),
                binding.shortNameEditText.text.toString(),
                binding.casEditText.text.toString(),
                binding.methodEditText.text.toString(),
                binding.densityMinEdtText.text.toString().toDouble(),
                binding.densityMaxEditText.text.toString().toDouble(),
                binding.meltMinEditText.text.toString().toInt(),
                binding.meltMaxEditeText.text.toString().toInt(),
                binding.maxServiceTempEditText.text.toString().toInt(),
                binding.colorEditText.text.toString())
            viewModel.addPolymer(item)
            Navigation.findNavController(view!!).popBackStack()
        }
        return binding.root
    }

}
