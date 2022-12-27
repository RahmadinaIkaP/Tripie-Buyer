package binar.academy.kelompok6.tripie_buyer.view.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentProfileBinding
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPref
    private val viewModel : ViewModelProfile by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

//        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner) { id ->
//            viewModel.getProfile(id.toInt())
//        }



        binding.btnLogout.setOnClickListener{
            clearData()
        }

        binding.navigateToProfile.setOnClickListener {
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
                viewModel.getProfile(it.toInt())
            }
            viewModel.getLiveDataProfile().observe(viewLifecycleOwner){
                when(it){
                    is ApiResponse.Loading -> {
                        Log.d("Loading", it.toString())
                    }
                    is ApiResponse.Success -> {
                        it.data?.data?.let{ user ->
                            val bundle = Bundle()
                            bundle.putInt("id", user.id)
                            bundle.putString("name", user.name)
                            bundle.putString("email", user.email)
                            bundle.putString("phone", user.phoneNumber)
                            bundle.putString("gambar", user.foto)
                            bundle.putString("address", user.address)
                            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
                        }
                    }
                    is ApiResponse.Error -> {
                        Log.d("Error", it.msg.toString())
                    }
                }
            }
        }
        checkUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearData(){
        GlobalScope.launch {
            sharedPref.removeToken()
        }
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
    }



    private fun showDataUSer(){
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
            viewModel.getProfile(it.toInt())
        }

        viewModel.getLiveDataProfile().observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Loading -> {
                    Log.d("Loading", it.toString())
                }
                is ApiResponse.Success -> {
                    it.data?.data?.let{ user ->
                        Glide.with(this)
                            .load(user.foto)
                            .into(binding.circleImageView2)
                        binding.tvNamaUser.text = user.name
                        binding.tvEmailUser.text = user.email
                    }

                }
                is ApiResponse.Error -> {
                    Log.d("Error", it.msg.toString())
                }
            }
        }
    }

    private fun checkUser(){
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it == "Undefined"){
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                Toast.makeText(context, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show()
            }else{
                showDataUSer()
            }
        }
    }

}