package binar.academy.kelompok6.tripie_buyer.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.response.Data
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseUser
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelProfile @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
    private var profile : MutableLiveData<ApiResponse<ResponseUser>> = MutableLiveData()

    fun getLiveDataProfile() : MutableLiveData<ApiResponse<ResponseUser>> = profile

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

}