package binar.academy.kelompok6.tripie_buyer.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.DataSearch
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHomeBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.PopularDestinationAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PopularDestinationAdapter.HomeInterface {

    private lateinit var binding: FragmentHomeBinding
    private val homeVm : HomeViewModel by viewModels()
    private lateinit var adapter : PopularDestinationAdapter

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun reqSearch() {
        homeVm.searchData(
            SearchTicketRequest(
                origin = binding.editTextDari.text.toString(),
                destinasi = binding.editTextKe.text.toString(),
                planeClass =
                if(binding.btnKelasBisnis.isActivated) "Business"
                else if(binding.btnKelasEkonomi.isActivated) "Economy"
                else if (binding.btnKelasFirst.isActivated) "First Class"
                else "",
                flightDate = binding.editTextTanggal.text.toString()
            )
        )
        homeVm.ambilLiveDataSearch().observe(viewLifecycleOwner){response->
            when(response){
                is ApiResponse.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    binding.progressbar.visibility = View.GONE
                    response.data?.let { showData(it.data) }
                }
                is ApiResponse.Error -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showData(listData: List<DataSearch>) {
        adapter = PopularDestinationAdapter(this)
        adapter.setData(listData)

        binding.apply {
            rvDestPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvDestPopular.adapter = adapter
        }
    }

    override fun onItemClick(dataHome: DataSearch) {
        val action = HomeFragmentDirections.actionHomeFragmentToBookingDetailFragment(/*dataHome*/)
        findNavController().navigate(action)
    }
}