package binar.academy.kelompok6.tripie_buyer.view.authentication.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.request.LoginRequest
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentLoginBinding
import binar.academy.kelompok6.tripie_buyer.utils.Constant.Companion.LOGIN_SUCCESSFUL
import binar.academy.kelompok6.tripie_buyer.view.authentication.viewmodel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPref: SharedPref
    private val auth : AuthenticationViewModel by viewModels()
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[LOGIN_SUCCESSFUL] = false

        binding.btnMasuk.setOnClickListener {
            if (binding.editTextEmail.text.toString().isEmpty() || binding.editTextPass.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                reqLogin()
            }
        }

        binding.btnBuatAkun.setOnClickListener {
            //masuk fragment register
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun reqLogin() {
        auth.loginUser(LoginRequest(
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPass.text.toString()
        ))
        auth.ambilLiveDataUser().observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    saveToken(it.data!!.token,it.data.id.toString())
                    Toast.makeText(requireContext(), "Login Success!", Toast.LENGTH_SHORT).show()
                    savedStateHandle[LOGIN_SUCCESSFUL] = true
                    findNavController().popBackStack()
                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveToken(token:String, idUser:String) {
        GlobalScope.launch {
            sharedPref.saveToken(token, idUser)
        }
    }

}