package com.example.filemanager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm:FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragments: MutableList<Fragment> = ArrayList()
    private val fragmentTitle: MutableList<String> = ArrayList()

    fun add(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitle.add(title)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitle[position]
    }

//    fun removeFragment(fragment: Fragment){
//        for (fragment1 in fragments){
//            if (fragment1 == fragment){
//                fragments.remove(fragment)
//            }
//        }
//    }

}