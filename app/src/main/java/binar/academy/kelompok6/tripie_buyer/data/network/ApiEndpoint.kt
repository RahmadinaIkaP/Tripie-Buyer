package binar.academy.kelompok6.tripie_buyer.data.network

import binar.academy.kelompok6.tripie_buyer.data.model.request.LoginRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.RegisterRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.UpdateProfileRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiEndpoint {

    @POST("register")
    fun register(@Body registerRequest: RegisterRequest) : Call<ResponseRegister>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest) : Call<ResponseLogin>

    @GET("history")
    fun getHistory() : Call<ResponseHistory>

    @POST("search-ticket")
    fun search(@Body searchRequest: SearchTicketRequest) : Call<ResponseSearchTicket>

    @PUT("user/{id}/update")
    fun updateProfile(@Path("id") id: Int, @Body updateProfileRequest: UpdateProfileRequest) : Call<ResponseUpdateProfile>

    @GET("user/{id}")
    fun getProfile(@Path("id") id: Int) : Call<ResponseUser>

}