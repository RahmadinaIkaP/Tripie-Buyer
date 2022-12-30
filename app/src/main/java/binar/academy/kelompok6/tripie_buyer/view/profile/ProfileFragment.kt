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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentProfileBinding
import binar.academy.kelompok6.tripie_buyer.utils.Constant
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPref
    private val viewModel : ViewModelProfile by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

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
        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.navigateToNotificaiton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_notificationFragment)
        }

        binding.btnLogout.setOnClickListener{
            clearData()
        }

        binding.navigateToProfile.setOnClickListener {
            sharedPref.getStatusGoogle.asLiveData().observe(viewLifecycleOwner){
                if (it == false){
                    sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ id ->
                        viewModel.getProfile(id.toInt())
                    }
                    viewModel.getLiveDataProfile().observe(viewLifecycleOwner){ response ->
                        when(response){
                            is ApiResponse.Loading -> {
                                Log.d("Loading", it.toString())
                            }
                            is ApiResponse.Success -> {
                                response.data?.data?.let{ user ->
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
                                Log.d("Error", response.msg.toString())
                            }
                        }
                    }
                }
                else{
                    Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment)
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
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.removeToken()

            if (GoogleSignIn.getLastSignedInAccount(requireContext()) != null){
                mGoogleSignInClient.signOut()
            }
        }
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
    }



    private fun showDataUSer(){
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ idUser ->
            sharedPref.getStatusGoogle.asLiveData().observe(viewLifecycleOwner){ isGoogle ->
                if (isGoogle.equals(false)){
                    viewModel.getProfile(idUser.toInt())
                }else{
                    setProfileGoogle()
                }
            }
        }

        viewModel.getLiveDataProfile().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Log.d("Loading", response.toString())
                }
                is ApiResponse.Success -> {
                    response.data?.data?.let{ user ->
                        sharedPref.getStatusGoogle.asLiveData().observe(viewLifecycleOwner){ statusG ->
                            if (statusG.equals(false)){
                                Glide.with(this)
                                    .load(user.foto)
                                    .into(binding.circleImageView2)
                                binding.tvNamaUser.text = user.name
                                binding.tvEmailUser.text = user.email
                            }

                        }
                    }
                }
                is ApiResponse.Error -> {
                    Log.d("Error", response.msg.toString())
                }
            }
        }
    }

    private fun setProfileGoogle() {
        sharedPref.getUsername.asLiveData().observe(viewLifecycleOwner){ username ->
            if (username != "Undefined"){
                binding.tvNamaUser.text = username
            }
        }
        sharedPref.getUrlImg.asLiveData().observe(viewLifecycleOwner){ url ->
            if (url != "Undefined"){
                Glide.with(this)
                    .load(url)
                    .into(binding.circleImageView2)
            }
        }
        sharedPref.getEmail.asLiveData().observe(viewLifecycleOwner){ email ->
            if (email != "Undefined"){
                binding.tvEmailUser.text = email
            }
        }
    }

    private fun checkUser(){
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){ token->
            if(token == "Undefined"){
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
//                Toast.makeText(context, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show()
            }else{
                showDataUSer()
            }
        }
    }

}