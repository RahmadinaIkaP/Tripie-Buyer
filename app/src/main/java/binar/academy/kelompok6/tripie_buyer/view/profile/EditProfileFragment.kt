package binar.academy.kelompok6.tripie_buyer.view.profile

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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
import okhttp3.MediaType.Companion.toMediaType
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
    private lateinit var image : MultipartBody.Part
    private val galleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        val contentResolver = requireActivity().contentResolver
        val type = contentResolver.getType(result!!)
        binding.ivProfile.setImageURI(result)

        val tempFile = File.createTempFile("image", "jpg",null)
        val inputStream = contentResolver.openInputStream(result!!)
        tempFile.outputStream().use {
            inputStream?.copyTo(it)
        }
        val reqBody : RequestBody = tempFile.asRequestBody(type!!.toMediaType())
        image = MultipartBody.Part.createFormData("image", tempFile.name, reqBody)
    }
    private val REQUEST_CODE_PERMISSION = 100


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
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
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
                val name = binding.editTextNama.text.toString().toRequestBody("multipart/form-data".toMediaType())
                val email = binding.editTextEmail.text.toString().toRequestBody("multipart/form-data".toMediaType())
                val address = binding.editTextAlamat.text.toString().toRequestBody("multipart/form-data".toMediaType())
                val phone = binding.editTextNotelp.text.toString().toRequestBody("multipart/form-data".toMediaType())
                var password = binding.editTextPassword.text.toString().toRequestBody("multipart/form-data".toMediaType())
                val confirmPassword = binding.editTextConfirmPassword.text.toString().toRequestBody("multipart/form-data".toMediaType())
                var foto = image

                ubahData(it.toInt(), address, email, confirmPassword, foto, name, phone)

//                if (binding.editTextNama.text.toString().isEmpty() || binding.editTextEmail.text.toString().isEmpty() || binding.editTextNotelp.text.toString().isEmpty() || binding.editTextAlamat.text.toString().isEmpty() || binding.editTextPassword.text.toString().isEmpty() || binding.editTextConfirmPassword.text.toString().isEmpty()){
//                    Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
//                } else if (binding.editTextPassword.text.toString() != binding.editTextConfirmPassword.text.toString()){
//                    Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
//                } else {
//                    ubahData(it.toInt(), address, email, confirmPassword, foto, name, phone)
//                }
            }
        }

        binding.ivProfile.setOnClickListener {
           addPhoto()
        }
    }

    fun ubahData(id : Int, Address : RequestBody,Email : RequestBody, encryptedPassword : RequestBody,Foto : MultipartBody.Part, Name : RequestBody,Phone_Number : RequestBody){
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
//                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

//    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let {
//            val contentResolver: ContentResolver = this.requireActivity().contentResolver
//            val type = contentResolver.getType(it)
//            imageUri = it
//
//            val fileNameimg = "${System.currentTimeMillis()}.png"
//            binding.ivProfile.setImageURI(it)
//            Toast.makeText(requireContext(), "$imageUri", Toast.LENGTH_SHORT).show()
//
//            val tempFile = File.createTempFile("and1-", fileNameimg, null)
//            imageFile = tempFile
//            val inputstream = contentResolver.openInputStream(uri)
//            tempFile.outputStream().use { result ->
//                inputstream?.copyTo(result)
//            }
//            val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
//            imageMultipart = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
//        }
//    }

    private fun showPermissionDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireActivity())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,)
        ){
            openGallery()
        }
    }

    private fun addPhoto() {
        checkingPermissions()
    }

}