package binar.academy.kelompok6.tripie_buyer.data.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity
@Parcelize
data class Favorit(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var idUser : String,
    val airportCode: String,
    val airportName: String,
    val city: String,
    val foto: String,
    val description: String) : Parcelable
