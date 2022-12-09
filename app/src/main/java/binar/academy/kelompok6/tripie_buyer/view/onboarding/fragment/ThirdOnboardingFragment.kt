package binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentSecondOnboardingBinding
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentThirdOnboardingBinding

class ThirdOnboardingFragment : Fragment() {
    private lateinit var binding : FragmentThirdOnboardingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentThirdOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.line3.setProgress(5000)
//        if(binding.line3.progress >= 5000){
//            Navigation.findNavController(view).navigate(R.id.action_thirdOnboardingFragment_to_homeFragment)
//        }

//        binding.btnGetStarted.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_onboardingFragment2_to_homeFragment2)
//        }
    }
}