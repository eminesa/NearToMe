package com.eminesa.neartome.fragment.pharmacydetail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.eminesa.neartome.databinding.FragmentPharmacyDetailBinding
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.model.*


class PharmacyDetailFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentPharmacyDetailBinding? = null
    private var marker: Marker? = null
    private val requestCode = 100
    private val runTimePermission = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private var hMap: HuaweiMap? = null

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!hasPermissions(*runTimePermission)) {
            ActivityCompat.requestPermissions(requireActivity(), runTimePermission, requestCode)
        }

        MapsInitializer.initialize(requireContext())

        if (binding == null)
            binding = FragmentPharmacyDetailBinding.inflate(inflater)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }

        binding?.mapView?.apply {
            onCreate(mapViewBundle)
            getMapAsync(this@PharmacyDetailFragment)
        }

        return binding?.root
    }

    private fun hasPermissions(vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onMapReady(map: HuaweiMap?) {
        hMap = map
        if (hasPermissions(runTimePermission[0], runTimePermission[1])) {
            setLocationEnabled(true)
        }

        // Enable the my-location layer.
        hMap?.isMyLocationEnabled = true
        // Enable the my-location icon.
        hMap?.uiSettings?.isMyLocationButtonEnabled = true

        if (arguments != null) {
            val pharmacyName = arguments?.getString("pharmacyName")
            val latitude = arguments?.getDouble("latitude")
            val longitude = arguments?.getDouble("longitude")

            val build = CameraPosition.Builder()
                .target(latitude?.let { longitude?.let { it1 -> LatLng(it, it1) } }).zoom(10f)
                .build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(build)
            hMap?.animateCamera(cameraUpdate)


            hMap?.setOnMarkerClickListener { marker ->

                val contentUrl: Uri =
                    Uri.parse("https://www.petalmaps.com/navigate/?saddr=$latitude&daddr=$longitude&type=drive&utm_source=fb")
                val intent = Intent(Intent.ACTION_VIEW, contentUrl)
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                }
                //Log.d("TAG", "Clicked marker ${marker.title}")
                true
            }

            marker = hMap?.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker()) // default marker
                    .title(pharmacyName) // maker title
                    .position(latitude?.let {
                        longitude?.let { it1 ->
                            LatLng(
                                it,
                                it1
                            )
                        }
                    }) // marker position
            )

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        binding?.mapView?.onSaveInstanceState(mapViewBundle)
    }

    /**
     * set location enable
     *
     * @param enable true:enable, false:disable
     */
    private fun setLocationEnabled(enable: Boolean) {
        hMap?.isMyLocationEnabled = enable
        hMap?.uiSettings?.isMyLocationButtonEnabled = enable
    }

    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding?.mapView?.onStop()
    }

    override fun onPause() {
        binding?.mapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        hMap = null
        marker = null
    }
}