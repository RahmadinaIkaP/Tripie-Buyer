package binar.academy.kelompok6.tripie_buyer.view.home.searchfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.SearchBundle
import binar.academy.kelompok6.tripie_buyer.data.model.response.DataSearch
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHasilSearchBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.SearchHomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HasilSearchFragment : Fragment(), SearchHomeAdapter.HasilSearchInterface {

    private lateinit var binding: FragmentHasilSearchBinding
//    private val homeVm : HomeViewModel by viewModels()
    private lateinit var adapter : SearchHomeAdapter
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHasilSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        val data = arguments?.getParcelable<SearchBundle>("searchResult") as SearchBundle

        binding.btnBackHome.setOnClickListener {
            findNavController().navigateUp()
        }

        setDataHasilSearch(data)
        setTextHasilSearch()
        showData(data.dataResponse.data)
    }

    private fun setTextHasilSearch() {
        binding.apply {
            sharedPref.apply {
                getOriginCode.asLiveData().observe(viewLifecycleOwner){
                    tvBandaraAsal.text = it
                }
                getOriginCity.asLiveData().observe(viewLifecycleOwner){
                    tvKotaAsal.text = it
                }
                getDestCode.asLiveData().observe(viewLifecycleOwner){
                    tvBandaraTujuan.text = it
                }
                getDestCity.asLiveData().observe(viewLifecycleOwner){
                    tvKotaTujuan.text = it
                }
            }
        }
    }

    private fun setDataHasilSearch(data: SearchBundle) {
        binding.apply {
            tvTanggal.text = data.dataRequest.flightDate
            tvJumlahPenumpang.text = "${data.dataRequest.totalPassenger} Penumpang"
            tvKelas.text = data.dataRequest.planeClass
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
        val bundle = Bundle()
        bundle.putParcelable("dataBuatBooking", arguments?.getParcelable<SearchBundle>("searchResult"))

        findNavController().navigate(R.id.action_hasilSearchFragment_to_bookingDetailFragment, bundle)
    }

}