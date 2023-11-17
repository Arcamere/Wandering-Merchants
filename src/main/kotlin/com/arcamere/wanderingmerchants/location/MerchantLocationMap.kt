package com.arcamere.wanderingmerchants.location

import org.bukkit.Location

@Suppress("UNCHECKED_CAST")
class MerchantLocationMap(val map: HashMap<String, MerchantLocation> = HashMap()) {
    val size get() = map.size
    operator fun set(name: String, location: MerchantLocation) = map.set(name, location)
    operator fun get(name: String) = map[name]
    operator fun iterator() = map.iterator()
    fun clone() = MerchantLocationMap(map.clone() as HashMap<String, MerchantLocation>)
    fun random() = map.values.random()
    fun add(location: MerchantLocation) = map.set(location.name, location)
    fun remove(location: MerchantLocation) = map.remove(location.name)
}