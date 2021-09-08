package com.example.appcentcase

import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.Toast
import android.provider.Settings
import android.os.PersistableBundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.ListFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var latitudeLabel: String? = null
    private var longitudeLabel: String? = null
    private var location:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        latitudeLabel = resources.getString(R.string.latitudeBabel)
        longitudeLabel = resources.getString(R.string.longitudeBabel)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }
    public override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        }
        else {
            getLastLocation()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
               Log.v("Location","$latitudeLabel")
                Log.v("LocationTwo","$longitudeLabel")

                location="36.96,-122.02"
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val myFragment = ListFragment()
                val bundle = Bundle()
                bundle.putString("message",location)
                myFragment.arguments = bundle
                fragmentTransaction.add(R.id.frameLayout, myFragment).commit()

            }
            else {
                Log.w(TAG, "getLastLocation:exception", task.exception)
                showMessage("No location detected. Make sure location is enabled on the device.")
            }
        }
    }
    private fun showMessage(string: String) {
        val container = findViewById<View>(R.id.frameLayout)
        if (container != null) {
            Toast.makeText(this@MainActivity, string, Toast.LENGTH_LONG).show()
        }
    }
    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        Toast.makeText(this@MainActivity, mainTextStringId, Toast.LENGTH_LONG).show()
    }
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }
    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        }
        else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}