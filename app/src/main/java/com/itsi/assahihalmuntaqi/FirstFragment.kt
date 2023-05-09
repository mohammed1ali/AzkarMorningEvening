package com.itsi.assahihalmuntaqi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsi.assahihalmuntaqi.databinding.FragmentFirstBinding
import com.itsi.assahihalmuntaqi.model.DuaData
import com.itsi.assahihalmuntaqi.model.DuaDb
import com.itsi.assahihalmuntaqi.primarylist.ItemsViewModel
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

        // ArrayList of class ItemsViewModel
        /*val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing ...
        for (i in 1..50) {
            data.add(ItemsViewModel("Item $i"))
        }*/
        val chNamesList = DuaDb().getChapterNames()

        // This will pass the ArrayList to our Adapter
        val adapter = PrimaryAdapter(chNamesList)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

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