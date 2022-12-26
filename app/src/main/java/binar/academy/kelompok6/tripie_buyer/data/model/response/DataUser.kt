package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class DataUser(
    @SerializedName("Address")
    val address: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Encrypted_Password")
    val encryptedPassword: String,
    @SerializedName("Foto")
    val foto: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Phone_Number")
    val phoneNumber: String,
    @SerializedName("Role")
    val role: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)