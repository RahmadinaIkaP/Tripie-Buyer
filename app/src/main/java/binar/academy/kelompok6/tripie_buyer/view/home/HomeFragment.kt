package binar.academy.kelompok6.tripie_buyer.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHomeBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.SearchHomeAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeVm : HomeViewModel by viewModels()
    private lateinit var adapter : SearchHomeAdapter
    private var isClickedbtnKelasBisnis = false
    private var isClickedbtnKelasEkonomi = false
    private var isClickedbtnKelasFirst = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
            reqSearch()
        }

        binding.apply {
            btnKelasEkonomi.setOnClickListener {
                binding.btnKelasEkonomi.setBackgroundColor(resources.getColor(R.color.blue))
                binding.btnKelasBisnis.setBackgroundColor(resources.getColor(R.color.light_gray))
                binding.btnKelasFirst.setBackgroundColor(resources.getColor(R.color.light_gray))

                binding.btnKelasEkonomi.setTextColor(resources.getColor(R.color.white))
                binding.btnKelasBisnis.setTextColor(resources.getColor(R.color.black))
                binding.btnKelasFirst.setTextColor(resources.getColor(R.color.black))

                isClickedbtnKelasEkonomi = true
                isClickedbtnKelasBisnis = false
                isClickedbtnKelasFirst = false
            }

            btnKelasBisnis.setOnClickListener {
                binding.btnKelasEkonomi.setBackgroundColor(resources.getColor(R.color.light_gray))
                binding.btnKelasBisnis.setBackgroundColor(resources.getColor(R.color.blue))
                binding.btnKelasFirst.setBackgroundColor(resources.getColor(R.color.light_gray))

                binding.btnKelasEkonomi.setTextColor(resources.getColor(R.color.black))
                binding.btnKelasBisnis.setTextColor(resources.getColor(R.color.white))
                binding.btnKelasFirst.setTextColor(resources.getColor(R.color.black))

                isClickedbtnKelasEkonomi = false
                isClickedbtnKelasBisnis = true
                isClickedbtnKelasFirst = false
            }

            btnKelasFirst.setOnClickListener {
                binding.btnKelasEkonomi.setBackgroundColor(resources.getColor(R.color.light_gray))
                binding.btnKelasBisnis.setBackgroundColor(resources.getColor(R.color.light_gray))
                binding.btnKelasFirst.setBackgroundColor(resources.getColor(R.color.blue))

                binding.btnKelasEkonomi.setTextColor(resources.getColor(R.color.black))
                binding.btnKelasBisnis.setTextColor(resources.getColor(R.color.black))
                binding.btnKelasFirst.setTextColor(resources.getColor(R.color.white))

                isClickedbtnKelasEkonomi = false
                isClickedbtnKelasBisnis = false
                isClickedbtnKelasFirst = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun reqSearch() {

        var kelas = ""

        if(isClickedbtnKelasEkonomi == true) kelas = "Economy"
        else if(isClickedbtnKelasBisnis == true) kelas = "Bussiness"
        else if (isClickedbtnKelasFirst) kelas = "First Class"
        else kelas = ""

        var destinationName = binding.editTextKe.text.toString()
        var flightDate = binding.editTextTanggal.text.toString()
        var originName = binding.editTextDari.text.toString()
        var planeClass = kelas
        var totalPassenger = 1

        var bundle = Bundle()
        bundle.putString("destinationName", destinationName)
        bundle.putString("flightDate", flightDate)
        bundle.putString("originName", originName)
        bundle.putString("planeClass", planeClass)
        bundle.putInt("totalPassenger", totalPassenger)

        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_hasilSearchFragment, bundle)
    }

}