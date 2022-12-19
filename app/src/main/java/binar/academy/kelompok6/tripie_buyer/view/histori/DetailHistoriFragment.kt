package binar.academy.kelompok6.tripie_buyer.view.histori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentDetailHistoriBinding

class DetailHistoriFragment : Fragment() {
    lateinit var binding : FragmentDetailHistoriBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailHistoriBinding.inflate(inflater, container, false)
        return binding.root
    }
}