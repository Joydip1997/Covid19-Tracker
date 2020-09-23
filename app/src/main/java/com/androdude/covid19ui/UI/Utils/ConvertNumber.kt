package com.androdude.covid19ui.UI.Utils

import java.text.DecimalFormat
import java.text.NumberFormat

class ConvertNumber
{

   companion object
   {
       fun formatValue(value: Double): String {
           var value = value
           val power: Int
           val suffix = " kmbt"
           var formattedNumber = ""
           val formatter: NumberFormat = DecimalFormat("#,###.#")
           power = StrictMath.log10(value).toInt()
           value = value / Math.pow(10.0, power / 3 * 3.toDouble())
           formattedNumber = formatter.format(value)
           formattedNumber = formattedNumber + suffix[power / 3]
           return if (formattedNumber.length > 4) formattedNumber.replace("\\.[0-9]+".toRegex(), "") else formattedNumber
       }
   }
}

