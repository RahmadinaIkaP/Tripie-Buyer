package binar.academy.kelompok6.tripie_buyer.view.whistlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentWhistlistBinding
import binar.academy.kelompok6.tripie_buyer.view.whistlist.adapter.FavoritAdapter
import binar.academy.kelompok6.tripie_buyer.view.whistlist.viewmodel.FavoritViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhistlistFragment : Fragment(), FavoritAdapter.FavoritInterface  {

    private val vmFav : FavoritViewModel by viewModels()
    private lateinit var adapter : FavoritAdapter
    private var _binding: FragmentWhistlistBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPref

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWhistlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        showDataFavorit()

    }

    private fun showDataFavorit() {
        adapter = FavoritAdapter(this)

        binding.apply {
            vmFav.getAllFav()
            vmFav.getAllFavObserver().observe(viewLifecycleOwner){
                adapter.setData(it)
            }

            rvFavorit.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvFavorit.adapter = adapter
        }
    }

    override fun onItemClick(favorit: Favorit) {
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
            val action = WhistlistFragmentDirections.actionWhistlistFragmentToDetailFlightFragment(
                Favorit(favorit.id, it, favorit.airportCode, favorit.airportName, favorit.city, favorit.foto, favorit.description))
            findNavController().navigate(action)
        }
    }

}