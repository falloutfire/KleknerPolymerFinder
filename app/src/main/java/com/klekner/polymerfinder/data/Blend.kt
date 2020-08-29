package com.klekner.polymerfinder.data

import androidx.room.*

@Entity(
    primaryKeys = ["polymerId", "blendId"]
)
data class PolymerBlend(
    val polymerId: Long,
    val blendId: Long
)


data class PolymerWithBlends (
    @Embedded
    var polymer: Polymer,
    @Relation(
        parentColumn = "id",
        entity = Polymer::class,
        entityColumn = "id",
        associateBy = Junction(
            value = PolymerBlend::class,
            parentColumn = "polymerId",
            entityColumn = "blendId"
        )
    )
    var blends: List<Polymer>?
)
