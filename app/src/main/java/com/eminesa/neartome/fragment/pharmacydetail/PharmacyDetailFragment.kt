package com.eminesa.neartome.fragment.pharmacydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eminesa.neartome.databinding.FragmentPharmacyDetailBinding

class PharmacyDetailFragment : Fragment() {
    private var binding: FragmentPharmacyDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentPharmacyDetailBinding.inflate(inflater)

        return binding?.root
    }

}