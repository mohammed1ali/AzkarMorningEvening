package com.itsi.almuntaqimorevn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsi.almuntaqimorevn.bookmarkslist.BookmarksAdapter
import com.itsi.almuntaqimorevn.databinding.FragmentBookmarksBinding
import com.itsi.almuntaqimorevn.model.DuaDb
import com.itsi.almuntaqimorevn.utils.BkmUtils
import com.itsi.almuntaqimorevn.utils.MyUtils

class BookmarksFragment : Fragment(), BookmarksAdapter.OnBookmarksEmptyListener {
    private var _binding: FragmentBookmarksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        val noBookmarksTv = _binding!!.tvNoBookmarks

        val recyclerview =_binding!!.recyclerviewBookmarks
        recyclerview.layoutManager = LinearLayoutManager(context)

        val bookmarksList = context?.let { BkmUtils().getAllBookmarks(it) }

        val bookmarkedDuaEvdList = DuaDb().getBookmarkedDuaEvd(bookmarksList)
        if (bookmarkedDuaEvdList.isNotEmpty()) {
            val fontSize = context?.let { MyUtils().getFontSize(it) }
            val adapter = BookmarksAdapter(bookmarkedDuaEvdList, fontSize?: 24, this)
            recyclerview.adapter = adapter
            noBookmarksTv.visibility = View.GONE
        } else {
            showNoBookmarksTv()
        }

        return binding.root
    }

    private fun showNoBookmarksTv() {
        val clBookmarks = _binding!!.clBookmarks
        val noBookmarksTv = _binding!!.tvNoBookmarks
        noBookmarksTv.updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToStart = clBookmarks.id
            topToTop = clBookmarks.id
            bottomToBottom = clBookmarks.id
            endToEnd = clBookmarks.id
            //add other constraints if needed
        }
        noBookmarksTv.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBookmarksEmpty() {
        try {
            showNoBookmarksTv()
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}