package binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.response.DataUpdateUser
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseUpdateProfile
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseUser
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelProfile @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
    private var profile : MutableLiveData<ApiResponse<ResponseUser>> = MutableLiveData()
    private var updateProfile : MutableLiveData<DataUpdateUser?> = MutableLiveData()

    fun getLiveDataProfile() : MutableLiveData<ApiResponse<ResponseUser>> = profile
    fun updateLiveDataProfile() : MutableLiveData<DataUpdateUser?> = updateProfile

    fun getProfile(id : Int){
        api.getProfile(id).enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        profile.postValue(ApiResponse.Success(it))
                    }
                }
                else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            profile.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        profile.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                profile.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }

    fun updateProfile(id : Int, Name : RequestBody, Email : RequestBody, Encrypted_Password : RequestBody, Foto : MultipartBody.Part, Address : RequestBody, Phone_Number : RequestBody){
        api.updateProfile(id,Name, Email, Encrypted_Password, Foto, Address, Phone_Number)
            .enqueue(object : Callback<DataUpdateUser>{
                override fun onResponse(
                    call: Call<DataUpdateUser>,
                    response: Response<DataUpdateUser>
                ) {
                    val body = response.body()
                    if (response.isSuccessful){
                        updateProfile.postValue(body)
                        Log.d("SUCCESS UPDATE", "$body")
                    }else{
                        updateProfile.postValue(null)
                        Log.d("FAILED UPDATE", "$body")
                    }
                }

                override fun onFailure(call: Call<DataUpdateUser>, t: Throwable) {
                    updateProfile.postValue(null)
                    Log.d("FAILED UPDATE", "${t.message}")
                }

            })
    }

}