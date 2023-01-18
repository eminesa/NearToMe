package com.eminesa.neartome.fragment.splash

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eminesa.neartome.R
import com.eminesa.neartome.databinding.FragmentSpashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var binding: FragmentSpashBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentSpashBinding.inflate(inflater)

        binding?.callLottie()

        return binding?.root
    }

    private fun FragmentSpashBinding.callLottie() {

        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.i("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.i("Animation:", "end")
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.i("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.i("Animation:", "repeat")
            }
        })
    }

    override fun onDestroyView() {
        binding?.animationView?.addAnimatorListener(null)
        binding = null
        super.onDestroyView()
    }
}

