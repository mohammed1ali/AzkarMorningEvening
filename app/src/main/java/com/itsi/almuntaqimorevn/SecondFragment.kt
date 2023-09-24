package com.itsi.almuntaqimorevn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsi.almuntaqimorevn.databinding.FragmentSecondBinding
import com.itsi.almuntaqimorevn.model.DuaDb
import com.itsi.almuntaqimorevn.primarylist.DuaEvdAdapter
import com.itsi.almuntaqimorevn.viewmodels.SharedViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var viewModel: SharedViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        // getting the recyclerview by its ..
        val recyclerview =_binding!!.recyclerviewDuaEvd

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        val chPos = 0 // arguments?.getInt("chPos", 0)
        val chName = getString(R.string.app_name)  // arguments?.getString("chName", "")

        //Toast.makeText(activity,"Position = $chPos", Toast.LENGTH_LONG).show()
        val duaEvidenceList = DuaDb().getDuaEvidenceList(chPos!!)

        //((AppCompatActivity)(getActivity())).getSupportActionBar()
        //supportActionBar.title = DuaDb().getChapterName(chPos)

        // This will pass the ArrayList to our Adapter
        val adapter = DuaEvdAdapter(duaEvidenceList)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        //_binding!!.tvDua.setText(duaEvidenceList.toString())

        viewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        viewModel.pageTitle(chName?:"")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}