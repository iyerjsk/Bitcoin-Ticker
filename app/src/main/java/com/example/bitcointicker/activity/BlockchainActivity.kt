package com.example.bitcointicker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.bitcointicker.Region
import com.example.bitcointicker.api.BlockChainApi
import com.example.bitcointicker.databinding.BlockchainActivityViewBinding
import com.example.bitcointicker.fragment.BlockchainFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BlockchainActivity : AppCompatActivity() {

    @Inject lateinit var api: BlockChainApi

    private var _binding: BlockchainActivityViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR)
        super.onCreate(savedInstanceState)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        _binding = BlockchainActivityViewBinding.inflate(layoutInflater).apply {
            viewPager = pager.apply { adapter =  pagerAdapter }
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val regions = Region.values()

        override fun getItemCount(): Int = regions.size

        override fun createFragment(position: Int): Fragment =
            BlockchainFragment.newInstance(regions[position])
    }

}