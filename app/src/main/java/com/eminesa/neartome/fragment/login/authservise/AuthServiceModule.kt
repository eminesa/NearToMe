package com.eminesa.neartome.fragment.login.authservise

import android.content.Context
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.hms.support.account.AccountAuthManager
import com.huawei.hms.support.account.request.AccountAuthParams
import com.huawei.hms.support.account.request.AccountAuthParamsHelper
import com.huawei.hms.support.account.service.AccountAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthServiceModule {

    @Provides
    @Singleton
    fun provideAccountAuthParams(): AccountAuthParams {
        return AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setAccessToken()
            .createParams()
    }

    @Provides
    @Singleton
    fun provideAccountAuthService(
        @ApplicationContext context: Context,
        huaweiIdAuthParams: AccountAuthParams
    ): AccountAuthService {
        return AccountAuthManager.getService(context, huaweiIdAuthParams)
    }

    @Provides
    @Singleton
    fun provideAGConnectAuth(): AGConnectAuth {
        return AGConnectAuth.getInstance()
    }
}