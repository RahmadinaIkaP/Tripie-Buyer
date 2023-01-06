package binar.academy.kelompok6.tripie_buyer.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataForBooking(
    val scheduleId: Int,
    val originName: String,
    val destinationName: String,
    val planeClass: String,
    val totalPassenger: Int,
    val flightType: String,
    val flightDate: String,
    val flightBackDate: String?,
    val departureHour: String,
    val arrivalHour: String,
    val price: Int
) : Parcelable
