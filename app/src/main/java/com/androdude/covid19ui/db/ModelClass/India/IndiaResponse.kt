package com.androdude.covid19_appupdate.db.ModelClass.India

import java.io.Serializable

data class IndiaResponse (
    val cases_time_series: List<CasesTimeSery>,
    val statewise: List<Statewise>,
    val tested: List<Tested>
): Serializable