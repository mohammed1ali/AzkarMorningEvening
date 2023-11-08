package com.itsi.almuntaqimorevn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsi.almuntaqimorevn.bookmarkslist.BookmarksAdapter
import com.itsi.almuntaqimorevn.databinding.FragmentBookmarksBinding
import com.itsi.almuntaqimorevn.model.DuaDb
import com.itsi.almuntaqimorevn.utils.BkmUtils
import com.itsi.almuntaqimorevn.utils.MyUtils

class BookmarksFragment : Fragment() {
    private var _binding: FragmentBookmarksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        val recyclerview =_binding!!.recyclerviewBookmarks
        recyclerview.layoutManager = LinearLayoutManager(context)

        val bookmarksList = context?.let { BkmUtils().getAllBookmarks(it) }

        val bookmarkedDuaEvdList = DuaDb().getBookmarkedDuaEvd(bookmarksList)
        if (bookmarkedDuaEvdList.isNotEmpty()) {
            val fontSize = context?.let { MyUtils().getFontSize(it) }
            val adapter = BookmarksAdapter(bookmarkedDuaEvdList, fontSize?: 24)
            recyclerview.adapter = adapter
            _binding!!.tvNoBookmarks.visibility = View.GONE
        } else {
            _binding!!.tvNoBookmarks.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}