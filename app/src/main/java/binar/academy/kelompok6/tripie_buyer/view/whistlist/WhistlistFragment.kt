package binar.academy.kelompok6.tripie_buyer.view.whistlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHomeBinding
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentWhistlistBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.FavoritAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.PopularDestinationAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.FavoritViewModel
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile

class WhistlistFragment : Fragment(), FavoritAdapter.FavoritInterface  {

    private val vmFav : FavoritViewModel by viewModels()
    private lateinit var adapter : FavoritAdapter
    private var _binding: FragmentWhistlistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWhistlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoritAdapter(this)
        vmFav.getAllFavObserver().observe(viewLifecycleOwner) {
            adapter.setData(it as ArrayList<Favorit>)
        }

        binding.apply {
            rvFavorit.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvFavorit.adapter = adapter
        }

    }

    override fun onItemClick(favorit: Favorit) {
        val action = WhistlistFragmentDirections.actionWhistlistFragmentToDetailWishlistFragment()
        findNavController().navigate(action)
    }

}