package com.androdude.covid19_appupdate.db.ModelClass.WordWide

data class WorldWideResponseItem(
    val active: Float,
    val activePerOneMillion: Double,
    val cases: Float,
    val casesPerOneMillion: Float,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Float,
    val criticalPerOneMillion: Float,
    val deaths: Float,
    val deathsPerOneMillion: Float,
    val oneCasePerPeople: Float,
    val oneDeathPerPeople: Float,
    val oneTestPerPeople: Float,
    val population: Float,
    val recovered: Float,
    val recoveredPerOneMillion: Double,
    val tests: Float,
    val testsPerOneMillion: Float,
    val todayCases: Float,
    val todayDeaths: Float,
    val todayRecovered: Float,
    val updated: Long
)