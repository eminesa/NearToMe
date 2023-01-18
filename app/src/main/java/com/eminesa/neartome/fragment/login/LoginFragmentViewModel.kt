package com.eminesa.neartome.fragment.login

import android.content.Intent
import android.service.autofill.UserData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.neartome.fragment.login.authservise.AuthServiceRepository
import com.huawei.agconnect.auth.AGConnectUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(private val authServiceRepository: AuthServiceRepository) :
    ViewModel() {

    var loginResult: LiveData<AGConnectUser>?= null

    fun signIn(intent: Intent?) = viewModelScope.launch {
        authServiceRepository.userSignIn(intent, onSuccess = { user ->
            Log.i("AUTH", user?.email.toString())
        }, onError = { error ->
            Log.i("AUTH", error?.message.toString())
        })
    }
}
