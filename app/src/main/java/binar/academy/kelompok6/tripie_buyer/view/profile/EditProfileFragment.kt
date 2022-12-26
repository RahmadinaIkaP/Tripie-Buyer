package binar.academy.kelompok6.tripie_buyer.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentEditProfileBinding
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    lateinit var binding : FragmentEditProfileBinding
    lateinit var sharedPref: SharedPref
    private val vmProfile : ViewModelProfile by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        var token = sharedPref.getToken.asLiveData().value
        var id = arguments?.getInt("id")
        var name = arguments?.getString("name")
        var email = arguments?.getString("email")
        var phone = arguments?.getString("phone")
        var gambar = arguments?.getString("gambar")
        var address = arguments?.getString("address")

        binding.editTextNama.setText(name)
        binding.editTextEmail.setText(email)
        binding.editTextNotelp.setText(phone)
        binding.editTextAlamat.setText(address)
        Glide.with(this).load(gambar).into(binding.ivProfile)


        binding.btnUbahData.setOnClickListener{
            ubahData()
        }
    }

    fun ubahData(){

    }
}