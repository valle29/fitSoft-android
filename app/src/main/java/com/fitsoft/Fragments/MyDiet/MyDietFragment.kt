package com.fitsoft.Fragments.MyDiet

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitsoft.Activities.MainActivity
import com.fitsoft.Adapters.PageAdapter
import com.fitsoft.R
import kotlinx.android.synthetic.main.fragment_my_diet.*

class MyDietFragment : Fragment() {

    var adapterViewPage: FragmentPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar!!.title = getString(R.string.menu_diet)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_diet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabs.addTab(tabs.newTab().setText("consumir por día"))
        tabs.addTab(tabs.newTab().setText("Hacer el súper"))
        tabs.tabGravity = TabLayout.GRAVITY_FILL

        adapterViewPage = PageAdapter(childFragmentManager,tabs.tabCount)

        vpPager.adapter = adapterViewPage
        vpPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                vpPager.currentItem = tab.position
            }

        })

    }
}
