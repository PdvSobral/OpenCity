package com.example.opencity.models

data class GPS(
    val latitude: Double,
    val longitude: Double
) {
    init {
        require(latitude.isFinite() && latitude in -90.0..90.0) {
            "Latitude must be finite and between -90 and 90"
        }
        require(longitude.isFinite() && longitude in -180.0..180.0) {
            "Longitude must be finite and between -180 and 180"
        }
    }

    override fun toString(): String = "$latitude;$longitude"

    companion object {
        fun fromString(value: String): GPS {
            val parts = value.trim().split(";")
            require(parts.size == 2) {
                "Expected GPS string in 'latitude,longitude' format"
            }

            val latitude = parts[0].trim().toDoubleOrNull()?: throw IllegalArgumentException("Invalid latitude: '${parts[0]}'")
            val longitude = parts[1].trim().toDoubleOrNull()?: throw IllegalArgumentException("Invalid longitude: '${parts[1]}'")

            return GPS(latitude, longitude)
        }
    }
}
