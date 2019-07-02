package com.fitsoft.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fitsoft.Fragments.MyDiet.FoodsDayFragment
import com.fitsoft.Fragments.MyDiet.MakeMarketFragment

class PageAdapter(fragmentManager: FragmentManager,internal var totalTabs: Int) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return FoodsDayFragment()
            1 -> return MakeMarketFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}