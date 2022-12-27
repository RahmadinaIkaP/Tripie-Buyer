package binar.academy.kelompok6.tripie_buyer.view.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentEditProfileBinding
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    lateinit var binding : FragmentEditProfileBinding
    lateinit var sharedPref: SharedPref
    private val viewModel : ViewModelProfile by viewModels()


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
            var id = arguments?.getInt("id",0)
            var name = binding.editTextNama.text.toString()
            var email = binding.editTextEmail.text.toString()
            var address = binding.editTextAlamat.text.toString()
            var phone = binding.editTextNotelp.text.toString()
            var password = binding.editTextPassword.text.toString()
            var confirmPassword = binding.editTextConfirmPassword.text.toString()
            var foto = "https://asset.kompas.com/crops/EGMctjIuF5J2LA8UR-dpVUGsGMQ=/0x0:590x393/750x500/data/photo/2022/02/25/62185810cdf80.jpg"

            if (binding.editTextNama.text.toString().isEmpty() || binding.editTextEmail.text.toString().isEmpty() || binding.editTextNotelp.text.toString().isEmpty() || binding.editTextAlamat.text.toString().isEmpty() || binding.editTextPassword.text.toString().isEmpty() || binding.editTextConfirmPassword.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (binding.editTextPassword.text.toString() != binding.editTextConfirmPassword.text.toString()){
                Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
            } else {
                ubahData(id.toString().toInt(), address, email, confirmPassword, foto, name, phone)
            }
        }
    }

    fun ubahData(id : Int, Address : String,Email : String, encryptedPassword : String,Foto : String, Name : String,Phone_Number : String){
        viewModel.updateProfile(id, Address, Email, encryptedPassword,Foto,Name, Phone_Number)
        viewModel.updateLiveDataProfile().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    Toast.makeText(context, "Edit Data berhasil", Toast.LENGTH_SHORT).show()
                    Log.d("Success: ", response.toString())
                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                }
                is ApiResponse.Error -> {
                    Toast.makeText(context, "Error, Ubah Data Gagal!", Toast.LENGTH_SHORT).show()
                    Log.d("Error: ", response.toString())
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}