package binar.academy.kelompok6.tripie_buyer.view.profile

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentEditProfileBinding
import binar.academy.kelompok6.tripie_buyer.view.profile.adapter.UriToFile
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    lateinit var binding : FragmentEditProfileBinding
    lateinit var sharedPref: SharedPref
    private val viewModel : ViewModelProfile by viewModels()

    companion object {

        const val PERMISSION_CODE = 101

        const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"

        const val MAX_NUMBER_REQUEST_PERMISSIONS = 2
    }

    private val permission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var getFile : File? = null
    private val imagePic =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri : Uri? ->
            if (uri != null) {
                val img = UriToFile(requireContext()).getImageBody(uri)
                getFile = img
                binding.ivProfile.setImageURI(uri)
            }
        }

    private var permissionRequestCount : Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        requestPermissionsIfNecessary()

        savedInstanceState?.let {
            permissionRequestCount = it.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        var name_ = arguments?.getString("name")
        var email_ = arguments?.getString("email")
        var phone_= arguments?.getString("phone")
        var gambar_ = arguments?.getString("gambar")
        var address_ = arguments?.getString("address")

        binding.editTextNama.setText(name_)
        binding.editTextEmail.setText(email_)
        binding.editTextNotelp.setText(phone_)
        binding.editTextAlamat.setText(address_)
        Glide.with(this).load(gambar_).into(binding.ivProfile)

        binding.btnUbahData.setOnClickListener{
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
                val name = binding.editTextNama.text.toString()
                val email = binding.editTextEmail.text.toString()
                val address = binding.editTextAlamat.text.toString()
                val phone = binding.editTextNotelp.text.toString()
                var password = binding.editTextPassword.text.toString()
                val confirmPassword = binding.editTextConfirmPassword.text.toString()

                if (binding.editTextNama.text.toString().isEmpty() || binding.editTextEmail.text.toString().isEmpty() || binding.editTextNotelp.text.toString().isEmpty() || binding.editTextAlamat.text.toString().isEmpty() || binding.editTextPassword.text.toString().isEmpty() || binding.editTextConfirmPassword.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else if (binding.editTextPassword.text.toString() != binding.editTextConfirmPassword.text.toString()){
                    Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
                } else {
                    val requestFile = getFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())
                    var imageMultipart : MultipartBody.Part = MultipartBody.Part.createFormData(
                        "image",
                        getFile!!.name,
                        requestFile
                    )
                    if (it != null){
                        viewModel.updateProfile(it.toInt(),name.toRequestBody("text/plain".toMediaTypeOrNull()),email.toRequestBody("text/plain".toMediaTypeOrNull()),confirmPassword.toRequestBody("text/plain".toMediaTypeOrNull()),imageMultipart,address.toRequestBody("text/plain".toMediaTypeOrNull()),phone.toRequestBody("text/plain".toMediaTypeOrNull()))
                        Toast.makeText(requireContext(), "Update Profile Berhasil", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Update Profile Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.ivProfile.setOnClickListener {
            requireActivity().intent.type = "image/*"
            imagePic.launch("image/*")
        }
    }

//    fun ubahData(id : Int, Address : RequestBody,Email : RequestBody, encryptedPassword : RequestBody,Foto : MultipartBody.Part, Name : RequestBody,Phone_Number : RequestBody){
//        viewModel.updateProfile(id, Address, Email, encryptedPassword,Foto,Name, Phone_Number)
//        viewModel.updateLiveDataProfile().observe(viewLifecycleOwner){ response ->
//            when(response){
//                is ApiResponse.Loading -> {
//                    Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
//                    Log.d("Loading: ", response.toString())
//                }
//                is ApiResponse.Success -> {
//                    Toast.makeText(context, "Edit Data berhasil", Toast.LENGTH_SHORT).show()
//                    Log.d("Success: ", response.toString())
//                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
//                }
//                is ApiResponse.Error -> {
//                    Toast.makeText(context, "Error, Ubah Data Gagal!", Toast.LENGTH_SHORT).show()
//                    Log.d("Error: ", response.toString())
////                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    //Request Permissions
    private fun requestPermissionsIfNecessary() {
        // Check if all required permissions are granted
        if (!checkAllPermissions()) {
            // When all required permissions are not granted yet

            if (permissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                // When the number of permission request retried is less than the max limit set
                permissionRequestCount += 1 // Increment the number of permission requests done
                // Request the required permissions for external storage access
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    permission,
                    PERMISSION_CODE
                )
            } else {
                // Disable the "Select Image" button when access is denied by the user
                binding.ivProfile.isEnabled = false
                // When the number of permission request retried exceeds the max limit set
                // Show a toast about how to update the permission for storage access
                Toast.makeText(
                    context,
                    "Go to Settings -> Apps and Notifications -> Flight Go Admin -> App Permissions and grant access to Storage.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkAllPermissions(): Boolean {
        // Boolean state to indicate all permissions are granted
        var hasPermissions = true
        // Verify all permissions are granted
        for (permission in permission) {
            hasPermissions = hasPermissions && isPermissionGranted(permission)
        }
        // Return the state of all permissions granted
        return hasPermissions
    }

    private fun isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(requireContext(), permission) ==
            PackageManager.PERMISSION_GRANTED

}