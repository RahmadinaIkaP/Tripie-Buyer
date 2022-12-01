package binar.academy.kelompok6.tripie_buyer.view.authentication.viewmodel

import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val api : ApiEndPoint) : ViewModel() {

}