package com.eminesa.neartome.network

import com.eminesa.neartome.request.NearByRequest

class Repository(private val apiService: ApiService) {

    suspend fun getNearBy(requestModel: NearByRequest?) = apiService.getNearBy(requestModel)

    suspend fun getPharmacy(il: String?, ilce: String?) = apiService.getPharmacy(il, ilce)

}