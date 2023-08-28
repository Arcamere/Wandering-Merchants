package com.arcamere.wanderingmerchants.location

import org.bukkit.Location
import org.bukkit.World

data class MerchantLocation(val name: String,
                            val world: World,
                            val x: Double,
                            val y: Double,
                            val z: Double,
                            val yaw: Float,
                            val pitch: Float) {
    constructor(name: String, location: Location): this(name, location.world!!, location.x, location.y, location.z, location.yaw, location.pitch)
    fun toLocation() = Location(world, x, y, z, yaw, pitch)
}