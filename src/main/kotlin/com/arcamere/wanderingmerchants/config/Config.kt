package com.arcamere.wanderingmerchants.config

import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant

data class Config(val merchants: ArrayList<Merchant>, val locations: MerchantLocationMap, val merchantCap: Int)
