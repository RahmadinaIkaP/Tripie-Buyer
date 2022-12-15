package binar.academy.kelompok6.tripie_buyer.view.authentication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.utils.EspressoIdlingResource
import binar.academy.kelompok6.tripie_buyer.data.model.request.LoginRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.RegisterRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseLogin
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseRegister
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
    private var liveDataUser : MutableLiveData<ApiResponse<ResponseLogin>> = MutableLiveData()
    private var postDataRegister : MutableLiveData<ApiResponse<ResponseRegister>> = MutableLiveData()

    fun ambilLiveDataUser() : MutableLiveData<ApiResponse<ResponseLogin>> = liveDataUser
    fun postDataRegister() : MutableLiveData<ApiResponse<ResponseRegister>> = postDataRegister

    fun loginUser(request : LoginRequest){
        liveDataUser.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()
        api.login(request).enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        liveDataUser.postValue(ApiResponse.Success(it))
                    }
                }
                else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            liveDataUser.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        liveDataUser.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                liveDataUser.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }

    fun registerUser(request : RegisterRequest){
        postDataRegister.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()
        api.register(request).enqueue(object : Callback<ResponseRegister>{
            override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        postDataRegister.postValue(ApiResponse.Success(it))
                    }
                }
                else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            postDataRegister.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        postDataRegister.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                postDataRegister.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }

}