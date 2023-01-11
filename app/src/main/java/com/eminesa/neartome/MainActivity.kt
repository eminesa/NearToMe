package com.eminesa.neartome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eminesa.neartome.enum.ResponseStatus
import com.eminesa.neartome.fragment.NearByViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.*
import com.eminesa.neartome.request.NearByRequest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: NearByViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        /*
            val request = NearByRequest("okul", "41", "29")
            viewModel.getNearBy(request).observe(this) { responseVersion ->
                when (responseVersion.status) {
                    ResponseStatus.LOADING -> {
                        Log.i("NEAR_BY_REQUEST", "LOADING")
                    }
                    ResponseStatus.SUCCESS -> {
                        val size = responseVersion.data?.result?.size
                    }
                    ResponseStatus.ERROR -> {
                        Log.e("NEAR_BY_REQUEST", "ERROR")
                    }
                }
            }


         viewModel.getPharmacy("Ankara", "Ulus").observe(this) { responseVersion ->
                when (responseVersion.status) {
                    ResponseStatus.LOADING -> {
                        Log.i("NEAR_BY_REQUEST", "LOADING")
                    }
                    ResponseStatus.SUCCESS -> {
                        val size = responseVersion.data?.result?.size
                    }
                    ResponseStatus.ERROR -> {
                        Log.e("NEAR_BY_REQUEST", "ERROR")
                    }
                }
            }*/

    }
}