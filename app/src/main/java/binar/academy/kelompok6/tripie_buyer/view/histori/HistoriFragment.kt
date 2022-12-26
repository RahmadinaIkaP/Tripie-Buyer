package binar.academy.kelompok6.tripie_buyer.view.histori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.response.Booking
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHistoriBinding
import binar.academy.kelompok6.tripie_buyer.view.histori.adapter.HistoryAdapter
import binar.academy.kelompok6.tripie_buyer.view.viewmodel.ViewModelHistory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoriFragment : Fragment() {
    lateinit var binding: FragmentHistoriBinding
    lateinit var adapterHistory: HistoryAdapter
    private val viewModelHistory : ViewModelHistory by viewModels()
    lateinit var sharedPref : SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        viewModelHistory.getHistory()
        viewModelHistory.getLiveDataHistory().observe(viewLifecycleOwner) {
            when(it){
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.dataBooking?.let { data ->
                        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner) { id ->
                            val filteredHistory = data.booking.filter { booking -> booking.userId.toString() == id }
                            showRvData(filteredHistory)
                        }
                      }
                    binding.rvHistory.adapter = adapterHistory
                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showRvData(booking: List<Booking>) {
        adapterHistory = HistoryAdapter(booking)
        adapterHistory.setData(booking)

        binding.apply {
            rvHistory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            rvHistory.adapter = adapterHistory
        }
    }
}