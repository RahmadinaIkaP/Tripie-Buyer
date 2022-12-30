package binar.academy.kelompok6.tripie_buyer.view.home.searchfragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.SearchBundle
import binar.academy.kelompok6.tripie_buyer.data.model.response.DataSearch
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHasilSearchBinding
import binar.academy.kelompok6.tripie_buyer.utils.Constant
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.SearchHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HasilSearchFragment : Fragment(), SearchHomeAdapter.HasilSearchInterface {

    private var _binding: FragmentHasilSearchBinding? = null
    private val binding get() = _binding!!
//    private val homeVm : HomeViewModel by viewModels()
    private lateinit var adapter : SearchHomeAdapter
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(Constant.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry){ success ->
                if (!success){
                    val startDestination = navController.graph.startDestinationId
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    navController.navigate(startDestination, null, navOptions)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHasilSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            sharedPref = SharedPref(requireContext())
            val data = arguments?.getParcelable<SearchBundle>("searchResult") as SearchBundle

            binding.btnBackHome.setOnClickListener {
                findNavController().navigate(R.id.action_hasilSearchFragment_to_homeFragment)
            }

            setDataHasilSearch(data)
            setTextHasilSearch()
            showData(data.dataResponse.data)
        }

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
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
            val flightDate = SimpleDateFormat("d MMMM y",
                Locale.getDefault()).format(dateFormat.parse(data.dataRequest.flightDate)!!)

            tvTanggal.text = flightDate
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
        checkUser()
    }

    private fun checkUser(){
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it == "Undefined"){
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_historiFragment_to_loginFragment)
                    Toast.makeText(context, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                }, 1000)
            }else{
                val bundle = Bundle()
                bundle.putParcelable("dataBuatBooking", arguments?.getParcelable<SearchBundle>("searchResult"))

                findNavController().navigate(R.id.action_hasilSearchFragment_to_bookingDetailFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}