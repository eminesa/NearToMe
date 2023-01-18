package com.eminesa.neartome.fragment.login

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eminesa.neartome.R
import com.eminesa.neartome.databinding.FragmentLoginBinding
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginFragmentViewModel by viewModels()
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null)
            binding = FragmentLoginBinding.inflate(inflater)

        binding?.btnLoginWithHuawei?.setOnClickListener {
            val authParams =
                HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAccessToken()
                    .createParams()
            val authService = HuaweiIdAuthManager.getService(requireContext(), authParams)
            signInWithHuaweiId.launch(authService.signInIntent)
        }

        return binding?.root
    }

    private var signInWithHuaweiId =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.signIn(result.data)
                findNavController().navigate(R.id.action_loginFragment_to_listPharmacyFragment)
            }
        }
}