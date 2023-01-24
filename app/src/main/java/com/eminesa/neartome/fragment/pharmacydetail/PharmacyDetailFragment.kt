package com.eminesa.neartome.fragment.pharmacydetail

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eminesa.neartome.databinding.FragmentPharmacyDetailBinding
import com.eminesa.neartome.enum.ResponseStatus
import com.eminesa.neartome.fragment.listpharmacy.ListPharmacyViewModel
import com.eminesa.neartome.network.DirectionsBaseRepo
import com.eminesa.neartome.request.DirectionsRequest
import com.eminesa.neartome.request.LatLngData
import com.eminesa.neartome.response.DirectionsResponse
import com.eminesa.neartome.response.Paths
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class PharmacyDetailFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: ListPharmacyViewModel by viewModels()
    private var binding: FragmentPharmacyDetailBinding? = null
    private var marker: Marker? = null
    var mPolyline:Polyline?= null

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

        if (arguments != null) {
            val city = arguments?.getString("city")
            val county = arguments?.getString("county")

            getPharmacy(city, county)
        }
        // getDirection("DAEDAOPYUdPj7qvHfqJQFbNElDN12UMUGOCv6dl4F54eOONr5x9QUlXQh9H81Fmu9hpKGmPz89EzFViggby52iXI4KYVU6hEalBBDQ==",directionsRequest)

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

    private fun getPharmacy(city: String?, county: String?) {
        viewModel.getPharmacy(city, county)
            .observe(viewLifecycleOwner) { responseVersion ->
                when (responseVersion.status) {
                    ResponseStatus.LOADING -> {
                        //internet kontrolu saglaman lazim
                    }
                    ResponseStatus.SUCCESS -> {
                        val firstPharmacy = responseVersion.data?.data?.first()
                        val build = CameraPosition.Builder()
                            .target(firstPharmacy?.latitude?.let {
                                firstPharmacy.longitude?.let { it1 ->
                                    LatLng(
                                        it,
                                        it1
                                    )
                                }
                            }).zoom(10f)
                            .build()
                        val cameraUpdate = CameraUpdateFactory.newCameraPosition(build)
                        hMap?.animateCamera(cameraUpdate)

                        responseVersion.data?.data?.map { pharmacy ->

                            marker = hMap?.addMarker(
                                MarkerOptions()
                                    .icon(BitmapDescriptorFactory.defaultMarker()) // default marker
                                    .title(pharmacy.EczaneAdi) // maker title
                                    .position(pharmacy.latitude?.let {
                                        pharmacy.longitude?.let { it1 ->
                                            LatLng(
                                                it,
                                                it1
                                            )
                                        }
                                    })
                            )
                        }

                        Log.i("RESPONSE", responseVersion.data?.data.toString())
                    }
                    ResponseStatus.ERROR -> {
                        Log.e("RESPONSE", "ERROR")
                    }
                }
            }
    }

    private fun getDirections(
        type: String,
        directionRequest: DirectionsRequest,
        callback: (item: Response<DirectionsResponse>) -> Unit
    ) {
        DirectionsBaseRepo().getInstance()?.getPipeLine(type, directionRequest)
            ?.enqueue(object :
                Callback<DirectionsResponse> {
                override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                    Log.d("TAG", "ERROR DIRECTIONS" + t.message)
                }

                override fun onResponse(
                    call: Call<DirectionsResponse>,
                    response: Response<DirectionsResponse>
                ) {
                    Log.d("TAG", "success DIRECTIONS" + response.message())
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.invoke(response)
                        }
                    }
                }
            })
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

        hMap?.setOnMarkerClickListener { marker ->

            var origin : LatLngData?= null

            hMap?.setOnMyLocationClickListener { location ->
                origin = LatLngData(location.latitude,  location.longitude)
            }

            val destination = LatLngData(marker.position.latitude, marker.position.longitude)
            val directionRequest = DirectionsRequest(origin, destination)
            getDirections(
                "DAEDAOPYUdPj7qvHfqJQFbNElDN12UMUGOCv6dl4F54eOONr5x9QUlXQh9H81Fmu9hpKGmPz89EzFViggby52iXI4KYVU6hEalBBDQ==",
                directionRequest
            ) { response ->
                  response.body()?.routes?.forEach { routes ->
                      routes.paths?.forEach { paths ->
                          addPolyLines(paths)
                      }
                  }
            }
            true
        }
    }

    private fun addPolyLines(path : Paths){

        if (null != mPolyline) {
            mPolyline?.remove()
            mPolyline = null
        }
        val options = PolylineOptions()

        options.add(LatLng(path.startLocation.lat, path.startLocation.lng))
        path.steps.forEach{
            it.polyline.forEach {it1->
                options.add(LatLng(it1.lat, it1.lng))
            }
        }
        options.add(LatLng(path.endLocation.lat, path.endLocation.lng))
        options.color(Color.BLUE)
        options.width(10f)

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
        binding?.mapView?.onDestroy()
        binding = null
        hMap = null
        marker = null
    }
}