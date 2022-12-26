package binar.academy.kelompok6.tripie_buyer.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHomeBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.PopularDestinationAdapter
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
    private lateinit var adapter : PopularDestinationAdapter
    private lateinit var flightType : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flightType = "One Way Trip"

        binding.apply {
            editTextDari.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listAirportOriginFragment)
            }

            getNameOriginAirport()

            editTextKe.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listAirportDestinationFragment)
            }

            getNameDestinationAirport()

            etDepartureDateOw.setOnClickListener {
                getDateFromDatePickerOneway()
            }

            etPlaneClass.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listPlaneClassFragment)
            }

            getPlaneClass()

            btnOneWay.setOnClickListener {
                linearLayout7.visibility = View.VISIBLE
                btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                linearLayout8.visibility = View.GONE
                btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                flightType = "One Way Trip"
            }

            btnRoundTrip.setOnClickListener {
                linearLayout7.visibility = View.GONE
                btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                linearLayout8.visibility = View.VISIBLE
                btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                flightType = "Round Trip"
            }

            etDepartureDateRt.setOnClickListener {
                getDateFromDatePickerRtDeparture()
            }

            etArriveDateRt.setOnClickListener {
                getDateFromDatePickerRtArrive()
            }

            btnSearch.setOnClickListener {
                reqSearch(flightType)
            }
        }
    }

    private fun getNameOriginAirport() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "namaAirportOrigin"
        )?.observe(viewLifecycleOwner) { name ->
            binding.editTextDari.setText(name)
        }
    }

    private fun getNameDestinationAirport() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "namaAirportDestination"
        )?.observe(viewLifecycleOwner) { name ->
            binding.editTextKe.setText(name)
        }
    }

    private fun getPlaneClass() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "planeClassName"
        )?.observe(viewLifecycleOwner){
            binding.etPlaneClass.setText(it)
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

    private fun reqSearch(checkTypeOw : String) {
        if (checkTypeOw == "One Way Trip"){
            homeVm.searchData(
                SearchTicketRequest(
                    originName = binding.editTextDari.text.toString(),
                    destinationName = binding.editTextKe.text.toString(),
                    planeClass = binding.etPlaneClass.text.toString(),
                    flightDate = binding.etDepartureDateOw.text.toString(),
                    totalPassenger = binding.etJumlahPenumpang.text.toString().toInt()
                )
            )
        }else if (checkTypeOw == "Round Trip"){
            homeVm.searchData(
                SearchTicketRequest(
                    originName = binding.editTextDari.text.toString(),
                    destinationName = binding.editTextKe.text.toString(),
                    planeClass = binding.etPlaneClass.text.toString(),
                    flightDate = binding.etDepartureDateRt.text.toString(),
                    totalPassenger = binding.etJumlahPenumpang.text.toString().toInt()
                )
            )
        }

        homeVm.ambilLiveDataSearch().observe(viewLifecycleOwner){response->
            when(response){
                is ApiResponse.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    binding.progressbar.visibility = View.GONE
//                    response.data?.let {  }
                    Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Error -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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