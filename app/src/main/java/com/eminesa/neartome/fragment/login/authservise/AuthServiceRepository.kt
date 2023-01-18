package com.eminesa.neartome.fragment.login.authservise

import android.content.Intent
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectUser
import com.huawei.agconnect.auth.HwIdAuthProvider
import com.huawei.hms.support.account.AccountAuthManager
import javax.inject.Inject

class AuthServiceRepository @Inject constructor(private val agConnectAuth: AGConnectAuth) {

    fun userSignIn(intent: Intent?, onSuccess: ((item: AGConnectUser?) -> Unit), onError: ((item: Exception?) -> Unit)){
        AccountAuthManager.parseAuthResultFromIntent(intent)?.let { task ->
            if (task.isSuccessful){
                task.result.let { authAccount->
                    val credential = HwIdAuthProvider.credentialWithToken(authAccount.accessToken)
                    agConnectAuth.signIn(credential).addOnSuccessListener {
                        onSuccess(it.user)
                    }.addOnFailureListener {
                        onError(it)
                    }
                }
            }
            else
               onError(Exception("Task is unsuccessful!"))
        }
    }
}