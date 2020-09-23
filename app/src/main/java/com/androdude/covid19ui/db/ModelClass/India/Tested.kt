package com.androdude.covid19_appupdate.db.ModelClass.India

data class Tested(
    val dailyrtpcrtests: String,
    val individualstestedperconfirmedcase: String,
    val positivecasesfromsamplesreported: String,
    val samplereportedtoday: String,
    val source: String,
    val source1: String,
    val source3: String,
    val testedasof: String,
    val testpositivityrate: String,
    val testsconductedbyprivatelabs: String,
    val testsperconfirmedcase: String,
    val testspermillion: String,
    val totalindividualstested: String,
    val totalpositivecases: String,
    val totalrtpcrtests: String,
    val totalsamplestested: String,
    val updatetimestamp: String
)