package com.eminesa.neartome

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eminesa.neartome.enum.ResponseStatus
import com.eminesa.neartome.fragment.NearByViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: NearByViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getPharmacy("istanbul", "avcilar").observe(this) { responseVersion ->
            when (responseVersion.status) {
                ResponseStatus.LOADING -> {
                    //internet kontrolu saglaman lazim
                }
                ResponseStatus.SUCCESS -> {
                    val date = responseVersion.data?.data?.get(0) ?: ""
                    Log.i("RESPONSE", date.toString())
                }
                ResponseStatus.ERROR -> {
                    Log.e("ERROR", "ERROR")
                }
            }
        }
    }

    /*fun annoEnqueue() {
        val map: MutableMap<String?, String?> = HashMap()
        map["Content-Type"] = "application/json"
        map["Authorization"] = "Bearer 6N148toByrhc0U6bBIG0txdVQEhGfDQByj5Aj0zynm6eoAaHaihlZ6R3oqwi"

        val apiClient = ApiClient.getApiClient().create(ApiNetworkService::class.java)
        apiClient.getPharmacy().enqueue(object : Callback<String?>() {
            override fun onResponse(p0: Submit<String?>?, response: Response<String?>?) {
                if (response?.isSuccessful == true) {
                    Toast.makeText(applicationContext, response.body, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Submit<String?>?, p1: Throwable?) {
                Toast.makeText(applicationContext, p1?.message, Toast.LENGTH_SHORT).show()
            }
        })

    } */
}