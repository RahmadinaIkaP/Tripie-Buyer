package binar.academy.kelompok6.tripie_buyer.view.whistlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentDetailWishlistBinding
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentWhistlistBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.FavoritAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.FavoritViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailWishlistFragment : Fragment() {

    private val vmFav : FavoritViewModel by viewModels()
    private lateinit var adapter : FavoritAdapter
    private var _binding: FragmentDetailWishlistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetail()
    }

    private fun getDetail() {
        val data = arguments?.getParcelable<Favorit>("dataWishlist") as Favorit

        vmFav.getSingleFav(data.id)
        vmFav.getFavObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    Glide.with(requireContext()).load(it.foto).into(ivFavorit)
                    txtJudulFavorit.text = it.airportName
                    txtNamaBandara.text = it.airportCode
                    lokasiTempat.text = it.city
                    txtDescFavorit.text = it.description
                }
            } else {
                Snackbar.make(binding.root, "Failed Load Data", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.light_gray))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                    .show()
            }
        }
    }
}