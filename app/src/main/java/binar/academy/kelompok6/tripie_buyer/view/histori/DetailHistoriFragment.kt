package binar.academy.kelompok6.tripie_buyer.view.histori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentDetailHistoriBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id = arguments?.getInt("id")
        var originAirport = arguments?.getString("originAirport")
        var destinationAirport = arguments?.getString("destinationAirport")

        binding.tvBandaraAsal.text = originAirport
        binding.tvBandaraTujuan.text = destinationAirport

        binding.buttonBack.setOnClickListener{
            findNavController().navigate(R.id.action_detailHistoriFragment_to_historiFragment)
        }
    }
}