package binar.academy.kelompok6.tripie_buyer.data.network

import binar.academy.kelompok6.tripie_buyer.data.model.request.*
import binar.academy.kelompok6.tripie_buyer.data.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
    @Multipart
    fun updateProfile(
        @Path("id") id: Int,
        @Part("name") Name: RequestBody,
        @Part("email") Email: RequestBody,
        @Part("encrypted_password") Encrypted_Password: RequestBody,
        @Part("phone_number") Phone_Number: RequestBody,
        @Part("address") Address: RequestBody,
        @Part Foto: MultipartBody.Part
        ) : Call<ResponseUpdateProfile>

    @GET("user/{id}")
    fun getProfile(@Path("id") id: Int) : Call<ResponseUser>

    @GET("get-airport")
    fun getAirport() : Call<AirportResponse>

    @POST("booking-ticket")
    fun bookingTicket(@Body bookingTicketRequest: BookingTicketRequest) : Call<ResponseBookingTicket>

}