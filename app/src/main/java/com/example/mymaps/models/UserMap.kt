package com.example.mymaps.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserMap(val title: String, val places: List<Place>) : Parcelable