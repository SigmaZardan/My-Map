package com.example.mymaps

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mymaps.databinding.ActivityCreateMapBinding
import com.google.android.gms.maps.model.Marker

private const val TAG = "CreateMapActivity"
class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateMapBinding
    private val markersList:MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // find the view by id

        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the title
        supportActionBar?.title = intent.getStringExtra(EXTRA_MAP_TITLE)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        mMap.setOnInfoWindowClickListener { markerToDelete ->
            Log.i(TAG,"OnWindowInfoClick --> Delete the marker")
            markersList.remove(markerToDelete)
            markerToDelete.remove()
        }

        mMap.setOnMapLongClickListener { latLng ->
            Log.i(TAG, "onMapLongPressed")
            showAlertDialogue(latLng)
        }

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    private fun showAlertDialogue(latLng: LatLng) {
       val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place,null)
        val dialogue = AlertDialog.Builder(this)
            .setTitle("Create a marker")
            .setView(placeFormView)
            .setPositiveButton("Ok",null )
            .setNegativeButton("Cancel",null)
            .show()

        // show the dialogue only when the user taps on the ok button
        dialogue.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
             val title = placeFormView.findViewById<EditText>(R.id.etTitle).text.toString()
            val description = placeFormView.findViewById<EditText>(R.id.etDescription).text.toString()

            if(title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this,"Title and Description cannot be empty!",Toast.LENGTH_LONG).show()
            }
            else {
                val marker = mMap.addMarker(
                    MarkerOptions().position(latLng).title(title).snippet(description)
                )
                if (marker != null) {
                    markersList.add(marker)
                }
                dialogue.dismiss()
            }
        }

    }


}