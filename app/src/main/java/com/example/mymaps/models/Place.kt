package com.example.mymaps.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable