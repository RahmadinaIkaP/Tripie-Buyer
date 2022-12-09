package binar.academy.kelompok6.tripie_buyer.view.splashscreen

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {
    private var _binding : FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startSplashScreen()
    }

    private fun startSplashScreen() {
        Handler().postDelayed({
            Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_onboardingFragment2)
        }, 1000)
    }
}