package binar.academy.kelompok6.tripie_buyer.data.network

import binar.academy.kelompok6.tripie_buyer.data.model.request.RegisterRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiEndpoint {

    @POST("register")
    fun register(@Body registerRequest: RegisterRequest) : Call<ResponseRegister>

}