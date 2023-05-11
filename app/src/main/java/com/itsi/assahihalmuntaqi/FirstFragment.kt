package com.itsi.assahihalmuntaqi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsi.assahihalmuntaqi.databinding.FragmentFirstBinding
import com.itsi.assahihalmuntaqi.model.DuaDb
import com.itsi.assahihalmuntaqi.primarylist.PrimaryAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        // getting the recyclerview by its ..
        val recyclerview =_binding!!.recyclerview

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        val chNamesList = DuaDb().getChapterEvd()

        // This will pass the ArrayList to our Adapter
        val adapter = PrimaryAdapter(chNamesList)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        adapter.setOnClickListener(object :
            PrimaryAdapter.OnClickListener {
            override fun onClick(position: Int, chName: String) {
                Toast.makeText(activity,"$position, $chName", Toast.LENGTH_LONG).show()
                DuaDb().getDuaEvidenceList(position)

                val bundle = Bundle()
                bundle.putInt("chPos", position)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}