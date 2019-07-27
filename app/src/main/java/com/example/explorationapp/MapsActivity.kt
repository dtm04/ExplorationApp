package com.example.explorationapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private var BOSTON = LatLng(42.0, 71.0)
    // vars to request and update user location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    // permissions object
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // uses locationCallback to update last location
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }
        createLocationRequest()
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
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        val foggyTileProvider = FoggyTileProvider(this.applicationContext)
        mMap.addTileOverlay(TileOverlayOptions().tileProvider(foggyTileProvider))
        setUpMap()
    }

    /**
     *
     */
    private fun setUpMap() {
        // Make sure app has been granted "access fine location" permissions
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        // adds 'my location' layer, draws blue dot for curr location, and adds center camera button
        mMap.isMyLocationEnabled = true

        // gives most recent location
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // if we have the most recent loc, then center camera
            // TODO: uncover map fog based on recent location
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }

    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        // set the marker icon
        markerOptions.icon(
            BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(resources, R.mipmap.ic_user_location)))
        // set address as marker title
        val titleStr = getAddress(location)
        markerOptions.title(titleStr)
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    private fun getAddress(latLng: LatLng): String {
        // GC object to turn lat/lng into human address
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // Get address from current lat/lng
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // If address then append to string
            if (addresses != null && addresses.isNotEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    /**
     * Request location updates.  If no permission, then request permission.
     */
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        // create locationRequest instance
        locationRequest = LocationRequest()
        // interaval = update rate
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // settings client and task to check location settings
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(this@MapsActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    /**
     * Override AppCompatActivity method, start update if request has RESULT_OK
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    /**
     * Override onPause to pause the location updates
     */
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    /**
     * Override onResume to resume location updates
     */
    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    private class FoggyTileProvider(context: Context) : TileProvider {

        private val mScaleFactor: Float = context.resources.displayMetrics.density * 0.6f
        private val mBorderTile: Bitmap

        init {
            /* Scale factor based on density, with a 0.6 multiplier to increase tile generation
             * speed */
            val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            borderPaint.style = Paint.Style.STROKE
            mBorderTile = Bitmap.createBitmap((TILE_SIZE_DP * mScaleFactor).toInt(),
                (TILE_SIZE_DP * mScaleFactor).toInt(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(mBorderTile)
            canvas.drawRect(0f, 0f, TILE_SIZE_DP * mScaleFactor, TILE_SIZE_DP * mScaleFactor,
                borderPaint)
        }

        override fun getTile(x: Int, y: Int, zoom: Int): Tile {
            val coordTile = drawTileCoords(x, y, zoom)
            val stream = ByteArrayOutputStream()
            coordTile.compress(Bitmap.CompressFormat.PNG, 0, stream)
            val bitmapData = stream.toByteArray()
            return Tile((TILE_SIZE_DP * mScaleFactor).toInt(),
                (TILE_SIZE_DP * mScaleFactor).toInt(), bitmapData)
        }

        private fun drawTileCoords(x: Int, y: Int, zoom: Int): Bitmap {
            // Synchronize copying the bitmap to avoid a race condition in some devices.
            var copy: Bitmap?
            synchronized(mBorderTile) {
                copy = mBorderTile.copy(Bitmap.Config.ARGB_8888, true)
            }
            val canvas = Canvas(copy!!)
            val tileCoords = "($x, $y)"
            val zoomLevel = "zoom = $zoom"
            /* Paint is not thread safe. */
            val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mTextPaint.textAlign = Paint.Align.CENTER
            mTextPaint.textSize = 18 * mScaleFactor
            canvas.drawText(tileCoords, TILE_SIZE_DP * mScaleFactor / 2,
                TILE_SIZE_DP * mScaleFactor / 2, mTextPaint)
            canvas.drawText(zoomLevel, TILE_SIZE_DP * mScaleFactor / 2,
                TILE_SIZE_DP.toFloat() * mScaleFactor * 2f / 3, mTextPaint)
            return copy as Bitmap
        }

        companion object {
            private const val TILE_SIZE_DP = 256
        }
    }
}
