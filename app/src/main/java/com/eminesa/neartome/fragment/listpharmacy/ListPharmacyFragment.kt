package com.eminesa.neartome.fragment.listpharmacy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.eminesa.neartome.R
import com.eminesa.neartome.databinding.FragmentListPharmacyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPharmacyFragment : Fragment() {

    private var binding: FragmentListPharmacyBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentListPharmacyBinding.inflate(inflater)

        binding?.apply {
            cityNameButton.setOnClickListener {

                if (binding?.cityNameEditText?.text!!.isEmpty()){
                    Toast.makeText(requireContext(), "Lutfen Sehir Giriniz", Toast.LENGTH_SHORT).show()
                }else{
                    val text = cityNameEditText.text.toString()
                    findNavController().navigate(
                        R.id.action_listPharmacyFragment_to_pharmacyDetailFragment, bundleOf(
                            "city" to  text,
                            "county" to "",
                        )
                    )
                }
            }
        }
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}