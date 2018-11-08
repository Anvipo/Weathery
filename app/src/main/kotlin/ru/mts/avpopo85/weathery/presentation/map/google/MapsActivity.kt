package ru.mts.avpopo85.weathery.presentation.map.google

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.map_fragment
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.activity.AbsBaseActivity
import ru.mts.avpopo85.weathery.presentation.base.utils.finishThisActivity
import ru.mts.avpopo85.weathery.presentation.utils.COORDINATES_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_BY_MAPS_RESULT_OK
import ru.mts.avpopo85.weathery.utils.common.showAlertDialog

class MapsActivity :
    AbsBaseActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        @Suppress("CAST_NEVER_SUCCEEDS")
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as? SupportMapFragment

        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        map.setOnMapClickListener(this)
        map.setOnMarkerClickListener(this)
        map.setOnMarkerDragListener(this)
    }

    override fun onMapClick(coordinates: LatLng?) {
        if (coordinates != null) {
            map.clear()

            val markerOptions: MarkerOptions = MarkerOptions().position(coordinates).draggable(true)

            map.addMarker(markerOptions)

            showConfirmation(coordinates)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker != null) {
            showConfirmation(marker.position)
        }
        //default behaviour should occur
        return false
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override val rootLayout: View by lazy { map_fragment as View }

    override fun onMarkerDragEnd(marker: Marker?) {
        if (marker != null) {
            showConfirmation(marker.position)
        }
    }

    override fun onMarkerDragStart(marker: Marker?) = Unit

    override fun onMarkerDrag(marker: Marker?) = Unit

    override fun changeTitle(title: String) = Unit

    private lateinit var map: GoogleMap

    private fun showConfirmation(coordinates: LatLng) {
        showAlertDialog(
            getString(R.string.is_this_your_location),
            getString(R.string.yes),
            getString(R.string.no),
            {
                val data = Intent().apply {
                    putExtra(COORDINATES_TAG, coordinates)
                }

                setResult(LOCATION_BY_MAPS_RESULT_OK, data)
                finishThisActivity()
            })
    }

}
