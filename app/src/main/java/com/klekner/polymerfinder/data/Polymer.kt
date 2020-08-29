package com.klekner.polymerfinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Polymer() {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var shortName: String? = null
    var casNumber: String? = null
    var methodSynthesis: String? = null
    var densityMin: Double? = null
    var densityMax: Double? = null
    var meltMin: Int? = null
    var meltMax: Int? = null
    var maxServiceTemp: Int? = null
    var color: String? = null
    var monomersStructure: String? = null

    constructor(
        fullName: String?,
        shortName: String?,
        casNumber: String?,
        methodSynthesis: String?,
        densityMin: Double?,
        densityMax: Double?,
        meltMin: Int?,
        meltMax: Int?,
        maxServiceTemp: Int?,
        color: String?
    ): this() {
        this.fullName = fullName
        this.shortName = shortName
        this.casNumber = casNumber
        this.methodSynthesis = methodSynthesis
        this.densityMin = densityMin
        this.densityMax = densityMax
        this.meltMin = meltMin
        this.meltMax = meltMax
        this.maxServiceTemp = maxServiceTemp
        this.color = color
    }
}