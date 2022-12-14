package binar.academy.kelompok6.tripie_buyer.view.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentFirstOnboardingBinding
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentOnboardingBinding
import binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment.FirstOnboardingFragment
import binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment.OnboardingFragment
import binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment.SecondOnboardingFragment
import binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment.ThirdOnboardingFragment
import com.bumptech.glide.Glide

class OnboardingAdapter(var fragment:Fragment) : FragmentStateAdapter(fragment) {

        class ViewHolder(var binding: FragmentOnboardingBinding) : RecyclerView.ViewHolder(binding.root)

        override fun createFragment(position: Int): Fragment {
                when(position){
                        0 -> return FirstOnboardingFragment()
                        1 -> return SecondOnboardingFragment()
                        2 -> return ThirdOnboardingFragment()
                        else -> return FirstOnboardingFragment()
                }
        }

        override fun getItemCount(): Int = 3

}