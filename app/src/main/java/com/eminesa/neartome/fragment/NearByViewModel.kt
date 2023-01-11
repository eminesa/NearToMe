package com.eminesa.neartome.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.eminesa.neartome.network.Repository
import com.eminesa.neartome.request.NearByRequest
import com.eminesa.neartome.response.GlobalResponse
import com.eminesa.neartome.response.NearByResponse
import com.eminesa.neartome.response.PharmacyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class NearByViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getNearBy(nearByRequest: NearByRequest?): LiveData<GlobalResponse<out NearByResponse>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(GlobalResponse.loading(null))
            try {
                emit(GlobalResponse.success(data = repository.getNearBy(nearByRequest)))
            } catch (e: Exception) {
                emit(
                    GlobalResponse.error(
                        data = null, e.localizedMessage
                    )
                )
            }
        }

    fun getPharmacy(il: String?, ilce:String?): LiveData<GlobalResponse<out PharmacyResponse>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(GlobalResponse.loading(null))
            try {
                emit(GlobalResponse.success(data = repository.getPharmacy(il, ilce)))
            } catch (e: Exception) {
                emit(
                    GlobalResponse.error(
                        data = null, e.localizedMessage
                    )
                )
            }
        }

}