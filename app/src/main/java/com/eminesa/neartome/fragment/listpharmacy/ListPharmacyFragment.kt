package com.eminesa.neartome.fragment.listpharmacy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.eminesa.neartome.R
import com.eminesa.neartome.adapter.PharmacyAdapter
import com.eminesa.neartome.databinding.FragmentListPharmacyBinding
import com.eminesa.neartome.enum.ResponseStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPharmacyFragment : Fragment() {

    private val viewModel: ListPharmacyViewModel by viewModels()
    private var binding: FragmentListPharmacyBinding? = null
    private var pharmacyAdapter: PharmacyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentListPharmacyBinding.inflate(inflater)

        getPharmacy()

        if (pharmacyAdapter == null)
            pharmacyAdapter = PharmacyAdapter(itemClickListener = { _, item ->
                findNavController().navigate(
                    R.id.action_listPharmacyFragment_to_pharmacyDetailFragment, bundleOf(
                        "pharmacyName" to item.EczaneAdi,
                        "latitude" to item.latitude,
                        "longitude" to item.longitude,
                        "city" to item.Sehir,
                        "county" to item.ilce,
                    )
                )
            })

        binding?.recyclerViewPharmacy?.apply {
            setHasFixedSize(false)
            /* addItemDecoration(
                 DividerItemDecoration(
                     requireContext(),
                     DividerItemDecoration.VERTICAL
                 )
             )*/
            adapter = pharmacyAdapter
        }

        // val user = AGConnectAuth.getInstance().currentUser
        val snapHelper: SnapHelper = LinearSnapHelper() // stay on one item
        snapHelper.attachToRecyclerView(binding?.recyclerViewPharmacy)

        return binding?.root
    }

    private fun getPharmacy() {
        viewModel.getPharmacy("istanbul", "")
            .observe(viewLifecycleOwner) { responseVersion ->
                when (responseVersion.status) {
                    ResponseStatus.LOADING -> {
                        //internet kontrolu saglaman lazim
                    }
                    ResponseStatus.SUCCESS -> {
                        pharmacyAdapter?.submitList(responseVersion.data?.data)

                        Log.i("RESPONSE", responseVersion.data?.data.toString())
                    }
                    ResponseStatus.ERROR -> {
                        Log.e("ERROR", "ERROR")
                    }
                }
            }
    }

    override fun onDestroy() {
        binding = null
        pharmacyAdapter = null
        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        pharmacyAdapter = null
        viewModel.getPharmacy(null, null).removeObservers(viewLifecycleOwner)
        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        super.onDestroyView()
    }
}