package binar.academy.kelompok6.tripie_buyer.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.DataSearch
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseSearchTicket
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHasilSearchBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.SearchHomeAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.HomeViewModel

class HasilSearchFragment : Fragment(), SearchHomeAdapter.HasilSearchInterface {

    private lateinit var binding: FragmentHasilSearchBinding
    private val homeVm : HomeViewModel by viewModels()
    private lateinit var adapter : SearchHomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHasilSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val destinationName = arguments?.getString("destinationName")
        val flightDate = arguments?.getString("flightDate")
        val originName = arguments?.getString("originName")
        val planeClass = arguments?.getString("planeClass")

        homeVm.searchData(SearchTicketRequest(destinationName!!, flightDate!!, originName!!, planeClass!!, 1))

        homeVm.ambilLiveDataSearch().observe(viewLifecycleOwner){response->
            when(response){
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.data!!.data.toString(), Toast.LENGTH_SHORT).show()
                    response.data?.let { showData(response.data.data) }
                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showData(listData: List<DataSearch>) {
        adapter = SearchHomeAdapter(this)
        adapter.setData(listData)

        binding.apply {
            rvHasilSearch.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvHasilSearch.adapter = adapter
        }
    }

    override fun onItemClick(dataHome: DataSearch) {
        val action = HasilSearchFragmentDirections.actionHasilSearchFragmentToBookingDetailFragment(/*dataHome*/)
        findNavController().navigate(action)
    }

}