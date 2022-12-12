package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Token")
    val token: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)