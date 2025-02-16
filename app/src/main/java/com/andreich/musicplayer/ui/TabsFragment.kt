package com.andreich.musicplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.andreich.musicplayer.MyApp
import com.andreich.musicplayer.R
import com.andreich.musicplayer.databinding.FragmentTabsBinding

class TabsFragment: Fragment() {

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(inflater, container, false)
        (activity?.application as MyApp).component.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavView, navHost.navController)
    }

}