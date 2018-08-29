package com.example.ishaqfakhrozi.lololonomic

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.location.LocationServices



import android.content.Intent
import android.provider.MediaStore

import android.widget.Toast
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*



class MapsActivity : FragmentActivity(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMarkerClickListener {


    private var mPermissionDenied = false
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    val CAMERA_REQUEST_CODE = 0


//    private var innerPaint: Paint? = null
//    private var borderPaint:Paint? = null





//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        fab_capture.setOnClickListener{
//
         val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
            finish()
        }

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.setOnMyLocationButtonClickListener(this)
        mMap!!.getUiSettings().setZoomControlsEnabled(true)
        mMap!!.setOnMarkerClickListener(this)
        setUpMap()

//        val jakarta = LatLng(-6.224432, 106.840241)
//        mMap!!.addMarker(MarkerOptions().position(jakarta).title("Im Here").draggable(true))
//        val zoomLevel = 16.0f //This goes up to 21
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(jakarta, zoomLevel))




    }
    override fun onMarkerClick(p0: Marker?) = false

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

        }
        mMap!!.isMyLocationEnabled = true


       fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val lokasikantor = LatLng(-6.224331, 106.840365)

                placeMarkerOnMap(lokasikantor)

                var circel =  mMap!!.addCircle(CircleOptions()
                        .center(lokasikantor)
                        .radius(130.0)
                        .strokeWidth(1f)
                        .fillColor(0x550000FF))
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasikantor, 16f))
                val userlocation = LatLng(location.latitude, location.longitude)
//                Toast.makeText(getBaseContext(), location.latitude.toString()+" dan "+location.longitude, Toast.LENGTH_LONG).show()
                val distance = FloatArray(2)
                Location.distanceBetween(userlocation.latitude, userlocation.longitude, circel.getCenter()
                        .latitude,circel.getCenter().longitude,distance);
                if (distance[0] > circel.getRadius())
                {
                    Toast.makeText(getBaseContext(), "Kamu di Luar Area Checkin", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Kamu Berada di Dalam Area Checkin", Toast.LENGTH_LONG).show()
                }

            }


        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true)
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap!!.isMyLocationEnabled = true
        }
    }


    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)
        // 2
        mMap!!.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            mPermissionDenied = false
        }
    }


    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(supportFragmentManager, "dialog")
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    companion object {

        private val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
