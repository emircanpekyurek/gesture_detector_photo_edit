package com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pekyurek.emircan.presentation.core.data.model.base.BaseResponse
import com.squareup.moshi.Json

@Entity(tableName = "offline_overlay")
data class Overlay(
    @PrimaryKey
    @Json(name = "overlayId")
    val overlayId: Int,
    @Json(name = "overlayName")
    val overlayName: String = "",
    @Json(name = "overlayPreviewIconUrl")
    var overlayPreviewIconUrl: String = "",
    @Json(name = "overlayUrl")
    var overlayUrl: String? = null
) : BaseResponse()