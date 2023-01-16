package com.eminesa.neartome.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.eminesa.neartome.network.Repository
import com.eminesa.neartome.response.GlobalResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dailyofspace.eminesa.dailyofspace.network.PharmacyResponse
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class NearByViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getPharmacy(il: String?, ilce: String?): LiveData<GlobalResponse<out PharmacyResponse>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(GlobalResponse.loading(null))
            try {
                emit(GlobalResponse.success(data = repository.getPharmacy( il, ilce)))
            } catch (e: Exception) {
                emit(
                    GlobalResponse.error(
                        data = null, e.localizedMessage
                    )
                )
            }
        }

}