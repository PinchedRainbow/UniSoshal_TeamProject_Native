package com.faheemsaleem.unisoshal.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.faheemsaleem.unisoshal.R

class ProximityFragment : Fragment() {
    private val permission = Manifest.permission.ACCESS_FINE_LOCATION
    private val PERMISSION_REQUEST_CODE = 10
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proximity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = view.findViewById(R.id.locationCheck)
        button.visibility = View.GONE
        checkPermission()
    }
    private fun checkPermission() {
        if (androidx.core.content.ContextCompat.checkSelfPermission(requireContext(), permission) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            button.visibility = View.VISIBLE
            button.setOnClickListener {
                requestPermissions(arrayOf(permission), PERMISSION_REQUEST_CODE)
            }
        } else {
            // get location
            getLocation()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
                    getLocation()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun animateTextView() {
        val textView = view?.findViewById<TextView>(R.id.textView7)
        textView?.animate()?.alpha(1f)?.scaleX(1.1F)?.scaleY(1.1F)?.setDuration(2000)?.setInterpolator {
            it * it * it * (it * (it * 6 - 15) + 10)
        }?.withEndAction {
            textView.animate().alpha(0.1f).setDuration(2000).scaleX(1F).scaleY(1F).setInterpolator {
                it * it * it * (it * (it * 6 - 15) + 10)
            }.withEndAction {
                animateTextView()
            }
        }
    }

    private fun changeStatus(status: String) {
        view?.findViewById<TextView>(R.id.textView7)?.text = status
    }

    private fun getLocation() {
        button.visibility = View.GONE
        view?.findViewById<TextView>(R.id.textView6)?.visibility = View.GONE
        view?.findViewById<TextView>(R.id.textView7)?.visibility = View.VISIBLE

        animateTextView()

        // if locationManager is not null, then it is already initialized and we don't need to do it again
        if (locationManager != null) {
            Toast.makeText(requireContext(), "Location manager is not null", Toast.LENGTH_SHORT).show()
            return
        }

        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                changeStatus("Finding people...")
                makeUseOfNewLocation(location)
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                // Called when the provider status changes. This method is called when a provider is unable to fetch a location or if the provider has recently become available after a period of unavailability.
                val statusString: String = when (status) {
                    LocationProvider.AVAILABLE -> "Available"
                    LocationProvider.OUT_OF_SERVICE -> "Out of Service"
                    LocationProvider.TEMPORARILY_UNAVAILABLE -> "Temporarily Unavailable"
                    else -> "Unknown"
                }
                changeStatus(statusString)
            }

            override fun onProviderEnabled(provider: String) {
                // Called when the provider is enabled by the user. If requestLocationUpdates is called on an already enabled provider, this method is called immediately.
                changeStatus("Getting location...")
            }

            override fun onProviderDisabled(provider: String) {
                // Called when the provider is disabled by the user. If requestLocationUpdates is called on an already disabled provider, this method is called immediately.
                changeStatus("Location disabled")
            }
        }

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f,
            locationListener as LocationListener
        )
    }

    private fun makeUseOfNewLocation(location: Location) {
        // boolean thats true if the user is not moving
        val isStill = location.speed < 0.1

        // if the user is not moving, then we can stop listening for location updates
        val dummyLocation = Location("dummyprovider")
        dummyLocation.latitude = 53.4729
        dummyLocation.longitude = -2.1684

        val distance = location.distanceTo(dummyLocation)
        val distance2DP = String.format("%.2f", distance) + "m"
        val accuracy2DP = String.format("%.2f", location.accuracy) + "m"

        val text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}\nAccuracy: ${accuracy2DP}\nYou are ${if (isStill) "" else "not "}still\nDistance to dummy location: $distance2DP"
        val textView = requireView().findViewById<TextView>(R.id.textView5)
        textView.text = text
    }


    override fun onDestroy() {
        super.onDestroy()
        locationManager?.removeUpdates(locationListener as LocationListener)
    }

    private fun getOtherLocations() {

    }

}
