package binar.academy.kelompok6.tripie_buyer.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.request.UpdateProfileRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseUpdateProfile
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelUpdateProfile @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
    private var updateProfile : MutableLiveData<ApiResponse<ResponseUpdateProfile>> = MutableLiveData()

    fun updateLiveDataProfile() : MutableLiveData<ApiResponse<ResponseUpdateProfile>> = updateProfile

    fun updateProfile(id : Int, Address : String,Email : String,Foto : String, Name : String,Phone_Number : String){
        api.updateProfile(id,UpdateProfileRequest(Address,Email,Foto,Name,Phone_Number)).enqueue(object : Callback<ResponseUpdateProfile>{
            override fun onResponse(call: Call<ResponseUpdateProfile>, response: Response<ResponseUpdateProfile>) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        updateProfile.postValue(ApiResponse.Success(it))
                    }
                }
                else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            updateProfile.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        updateProfile.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUpdateProfile>, t: Throwable) {
                updateProfile.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }
}
