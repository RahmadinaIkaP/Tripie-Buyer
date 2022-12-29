package binar.academy.kelompok6.tripie_buyer.data.network

import binar.academy.kelompok6.tripie_buyer.data.model.request.*
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

    @GET("get-airport")
    fun getAirport() : Call<AirportResponse>

    @POST("booking-ticket")
    fun bookingTicket(@Body bookingTicketRequest: BookingTicketRequest) : Call<ResponseBookingTicket>

    @GET("notification/{id}")
    fun getNotification(@Path("id") id: Int) : Call<GetNotificationResponse>

}