package binar.academy.kelompok6.tripie_buyer.data.network

import binar.academy.kelompok6.tripie_buyer.data.model.request.LoginRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.RegisterRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseHistory
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseLogin
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseRegister
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseSearchTicket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {

    @POST("register")
    fun register(@Body registerRequest: RegisterRequest) : Call<ResponseRegister>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest) : Call<ResponseLogin>

    @GET("history")
    fun getHistory() : Call<ResponseHistory>

    @POST("search-ticket")
    fun search(@Body searchRequest: SearchTicketRequest) : Call<ResponseSearchTicket>

}