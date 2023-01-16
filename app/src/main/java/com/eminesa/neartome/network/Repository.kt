package com.eminesa.neartome.network

class Repository(private val apiService: ApiService) {

    suspend fun getPharmacy(il: String?, ilce: String?) = apiService.getPharmacy(il, ilce)

}