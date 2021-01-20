package hu.bme.aut.android.tobaccoradar.ui.fragments

import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.ui.activities.DetailsActivity

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        var latLng: LatLng
        for (shop in TobaccoShopFragment.tList) {
            latLng = LatLng(shop.latitude, shop.longitude)
            if (shop.isOpen) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(shop.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(100.0f))
                )
            } else {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(shop.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(1.0f))
                )
            }

            val cameraPosition : CameraPosition = CameraPosition.builder()
                .target(latLng)
                .zoom(13.5f)
                .build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
        googleMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                var latLng : LatLng
                for (markerShop in TobaccoShopFragment.tList) {
                    latLng = LatLng(markerShop.latitude, markerShop.longitude)
                    if(latLng == marker.position){
                        val intent = Intent(context, DetailsActivity::class.java)
                        val bundle = Bundle()
                        bundle.putParcelable(TobaccoShopFragment.GET_SELECTED_SHOP, markerShop)
                        intent.putExtra(TobaccoShopFragment.GET_SELECTED_SHOP_BUNDLE, bundle)
                        startActivity(intent)
                        break
                    }
                }
                return false
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
