package com.example.filemanager2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import org.w3c.dom.Text

class HomeFragment : Fragment() {
    internal lateinit var viewPagerAdapter :ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)

        viewPagerAdapter.add(AllFragment(),"All")
        viewPagerAdapter.add(DocFragment(),"doc")
        viewPagerAdapter.add(DocxFragment(),"docx")
        viewPagerAdapter.add(TextFragment(),"txt")

//        viewPagerAdapter.add(AlbumsFragment(),"Albums")

        Log.i("orientation","in home frag")
        viewPager.adapter = viewPagerAdapter

        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
//        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

        return view
    }
}