package binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel

import androidx.core.content.ContentProviderCompat.requireContext
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseUpdateProfile
import binar.academy.kelompok6.tripie_buyer.data.model.response.ResponseUser
import org.junit.Assert.*
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.view.profile.ProfileFragment
import binar.academy.kelompok6.tripie_buyer.view.profile.adapter.UriToFile
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import org.junit.Before
import org.junit.Test

class ViewModelProfileTest {
    lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getprofileTest() : Unit = runBlocking {
        val response = mockk<Call<ResponseUser>>()
        every {
            runBlocking {
                service.getProfile(1)
            }
        } returns response

        val result = service.getProfile(1)

        verify {
            runBlocking {
                service.getProfile(1)
            }
        }
        assertEquals(result, response)
    }
}