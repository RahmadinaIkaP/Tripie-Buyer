package binar.academy.kelompok6.tripie_buyer.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHomeBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.PopularDestinationAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.AirportViewModel
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.HomeViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeVm : HomeViewModel by viewModels()
    private val vmAirport : AirportViewModel by viewModels()
    private lateinit var adapter : PopularDestinationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            editTextDari.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listAirportOriginFragment)
            }

            editTextKe.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listAirportDestinationFragment)
            }

            etDepartureDateOw.setOnClickListener {
                getDateFromDatePickerOneway()
            }

            val listPlaneClass = arrayListOf("economy", "business", "first Class")
            val planeClassAdapter = ArrayAdapter(requireContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, listPlaneClass)

            ActlistPlaneClass.setAdapter(planeClassAdapter)

            btnOneWay.setOnClickListener {
                linearLayout7.visibility = View.VISIBLE
                btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                linearLayout8.visibility = View.GONE
                btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
            }

            btnRoundTrip.setOnClickListener {
                linearLayout7.visibility = View.GONE
                btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                linearLayout8.visibility = View.VISIBLE
                btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }

            etDepartureDateRt.setOnClickListener {
                getDateFromDatePickerRtDeparture()
            }

            etArriveDateRt.setOnClickListener {
                getDateFromDatePickerRtArrive()
            }

            btnSearch.setOnClickListener {
                reqSearch()
            }
        }
    }

    private fun getDateFromDatePickerOneway() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal Penerbangan")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etDepartureDateOw.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getDateFromDatePickerRtDeparture() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal Penerbangan")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etDepartureDateRt.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getDateFromDatePickerRtArrive() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal Penerbangan")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etArriveDateRt.setText(dateFormat.format(Date(it)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun reqSearch() {
//        homeVm.searchData(
//            SearchTicketRequest(
//                origin = binding.editTextDari.text.toString(),
//                destinasi = binding.editTextKe.text.toString(),
//                planeClass =
//                if(binding.btnKelasBisnis.isActivated) "Business"
//                else if(binding.btnKelasEkonomi.isActivated) "Economy"
//                else if (binding.btnKelasFirst.isActivated) "First Class"
//                else "",
//                flightDate = binding.editTextTanggal.text.toString()
//            )
//        )
        homeVm.ambilLiveDataSearch().observe(viewLifecycleOwner){response->
            when(response){
                is ApiResponse.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    binding.progressbar.visibility = View.GONE
                    response.data?.let {  }
                }
                is ApiResponse.Error -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    private fun showData(listData: List<DataSearch>) {
//        adapter = PopularDestinationAdapter(this)
//        adapter.setData(listData)
//
//        binding.apply {
//            rvDestPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            rvDestPopular.adapter = adapter
//        }
//    }
    
}