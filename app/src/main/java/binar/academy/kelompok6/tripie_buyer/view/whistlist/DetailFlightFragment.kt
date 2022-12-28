package binar.academy.kelompok6.tripie_buyer.view.whistlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentDetailWishlistBinding
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentWhistlistBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.FavoritAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.AirportViewModel
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.FavoritViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailFlightFragment : Fragment() {

    private val vmFav : FavoritViewModel by viewModels()
    private lateinit var adapter : FavoritAdapter
    private var _binding: FragmentDetailWishlistBinding? = null
    private val binding get() = _binding!!
    private val args : DetailFlightFragmentArgs by navArgs()
    private lateinit var sharedPref: SharedPref
    private var isClicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_detailWishlistFragment_to_popularDestinationFragment)
        }

        getDetail()
    }

    private fun getDetail() {
        val data = args.dataAirport

        binding.apply {
            Glide.with(requireContext()).load(data.foto).into(ivFavorit)
            txtJudulFavorit.text = data.airportName
            txtNamaBandara.text = data.airportCode
            lokasiTempat.text = data.city
            txtDescFavorit.text = data.description
        }

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                val count = vmFav.cekFav(data.id)
                isClicked = if (count > 0){
                    binding.btnFavorit.setImageResource(R.drawable.ic_favorite)
                    true
                }
                else{
                    binding.btnFavorit.setImageResource(R.drawable.ic_unfavorite)
                    false
                }
            }
        }

        binding.btnFavorit.setOnClickListener {
            isClicked = !isClicked
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
                if (it != null){
                    checkButtonClicked(isClicked, Favorit(data.id, it, data.airportCode, data.airportName, data.city, data.foto, data.description))
                }
            }
        }
    }

    private fun checkButtonClicked(isClicked : Boolean, data: Favorit) {
        if (isClicked){
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
                vmFav.insertFav(Favorit(data.id, it, data.airportCode, data.airportName, data.city, data.foto, data.description))
            }
            binding.btnFavorit.setImageResource(R.drawable.ic_favorite)
            Snackbar.make(binding.root, "Favorit Berhasil Ditambahkan!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.light_gray))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                .show()
        }else{
            vmFav.deleteFav(data)
            binding.btnFavorit.setImageResource(R.drawable.ic_unfavorite)
            Snackbar.make(binding.root, "Favorit Terhapus!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.light_gray))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                .show()
        }

    }
}