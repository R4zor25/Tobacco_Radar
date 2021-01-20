package hu.bme.aut.android.tobaccoradar.network.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TobaccoShopListModel(
    val id: Int,
    val name: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val isOpen: Boolean,
    val openingSchedule: String?,
    val description: String?
) : Parcelable