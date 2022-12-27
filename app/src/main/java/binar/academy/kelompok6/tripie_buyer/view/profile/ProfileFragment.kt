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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentProfileBinding
import binar.academy.kelompok6.tripie_buyer.utils.Constant.Companion.LOGIN_SUCCESSFUL
import binar.academy.kelompok6.tripie_buyer.view.authentication.login.LoginFragment
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPref
    private val viewModel : ViewModelProfile by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LOGIN_SUCCESSFUL)
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

        binding.btnLogout.setOnClickListener{
            clearData()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment5)
        }

        binding.navigateToProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        checkUser()
        showDataUSer()

    }

    private fun checkUser(){
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){ token ->
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ userId ->
                Handler(Looper.getMainLooper()).postDelayed({
                    if(token == "Undefined" && userId == "Undefined"){
                        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                        Toast.makeText(context, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.getProfile(userId.toInt())
                    }
                }, 1000)
            }
        }
    }

    private fun showDataUSer(){
        viewModel.getLiveDataProfile().observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Loading -> {
                    Log.d("Loading", it.toString())
                }
                is ApiResponse.Success -> {
                    it.data?.data?.let{ user ->
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

    private fun clearData(){
        GlobalScope.launch {
            sharedPref.removeToken()
        }
        Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}