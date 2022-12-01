package binar.academy.kelompok6.tripie_buyer.view.authentication.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMasuk.setOnClickListener {
            //username dan password di login
            val inputEmail = binding.editTextEmail.text.toString()
            val inputPass = binding.editTextPass.text.toString()

            if(inputEmail == "" || inputPass == ""){
                Toast.makeText(context, "Fill In Username and Password!", Toast.LENGTH_SHORT).show()
            }
            else {
                //seleksi data yg diinput dengan data di API
                //ada -> navigasi ke home, gada -> toast no data
            }
        }

        binding.btnBuatAkun.setOnClickListener {
            //masuk fragment register
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_profileFragment)
        }
    }
}