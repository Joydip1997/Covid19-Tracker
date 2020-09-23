package com.androdude.covid19ui.UI

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.androdude.covid19_appupdate.db.ModelClass.India.IndiaResponse
import com.androdude.covid19_appupdate.db.ModelClass.Total.TotalResponse
import com.androdude.covid19_appupdate.db.ViewModel.IndiaDataViewModel
import com.androdude.covid19ui.R
import com.androdude.covid19ui.UI.Utils.ConvertNumber
import com.androdude.covid19ui.UI.Utils.DialogBox
import com.androdude.covid19ui.UI.Utils.NoInternetDialog
import com.androdude.covid19ui.db.local_database.entites.IndiaCachedEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_india.view.*
import org.eazegraph.lib.models.PieModel
import retrofit2.Response


class IndiaFragment : Fragment(R.layout.fragment_india) {


    //View models
    private lateinit var localDBViewModel: IndiaDataViewModel
    private lateinit var mViewModel1: IndiaDataViewModel

    //Response Objects
    private lateinit var IndiaResponseData: IndiaResponse
    private lateinit var response1: LiveData<Response<IndiaResponse>>

    //Dialogbox objects
    private lateinit var loadingDialog: DialogBox
    private lateinit var noInternetDialog : NoInternetDialog







    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setupUI(view)
        view.india_frag_layout.visibility=View.VISIBLE

        //Initialization
        loadingDialog =  DialogBox(requireActivity())
        noInternetDialog = NoInternetDialog(requireActivity())
        loadingDialog.buildDialog()
        noInternetDialog.buildDialog()
        loadingDialog.show()
        mViewModel1 = ViewModelProvider(this).get(IndiaDataViewModel::class.java)
        localDBViewModel = ViewModelProviders.of(requireActivity()).get(IndiaDataViewModel::class.java)

        view.india_frag_layout.visibility = View.GONE
        if(arguments != null)
        {
            if(requireArguments().getBoolean("STOP"))
            {
                loadingDialog.stop()
            }
        }



        if(isNetworkConnected())
        {
            response1  =  mViewModel1.getIndiaDetails()
            response1.observe(requireActivity(), Observer {

                if(it.isSuccessful)
                {
                    IndiaResponseData = it.body()!!
                    loadingDialog.stop()
                    view.india_frag_layout.visibility = View.VISIBLE
                    setIndiaDetails(IndiaResponseData,view)

                }

            })

        }
        else{
            val localInstance : LiveData<IndiaCachedEntity>  = localDBViewModel.getIndiaCachedData()
            localInstance.observe(requireActivity(), Observer {
                val handler = Handler()
                handler.postDelayed(Runnable {
                    loadingDialog.stop()
                    noInternetDialog.show()
                },2500)
            })
        }

    }





    private fun setIndiaDetails(response: IndiaResponse,view: View) {
        view.total_cases.text = ConvertNumber.formatValue(response.cases_time_series[response.cases_time_series.size-1].totalconfirmed.toDouble())
        val active = Integer.parseInt( response.cases_time_series[response.cases_time_series.size-1].totalconfirmed) - (
                Integer.parseInt(response.cases_time_series[response.cases_time_series.size-1].totalrecovered) +
                        Integer.parseInt(response.cases_time_series[response.cases_time_series.size-1].totaldeceased)
                )
        view.active_cases.text = ConvertNumber.formatValue(active.toDouble())
        view.recovered_cases.text =ConvertNumber.formatValue( response.cases_time_series[response.cases_time_series.size-1].totalrecovered.toDouble())
        view.death_cases.text = ConvertNumber.formatValue(response.cases_time_series[response.cases_time_series.size-1].totaldeceased.toDouble())

        val chart = chartData( response.cases_time_series[response.cases_time_series.size-1].totalconfirmed,
                active.toString(),response.cases_time_series[response.cases_time_series.size-1].totalrecovered,
                response.cases_time_series[response.cases_time_series.size-1].totaldeceased)

        view.last_updated.text = response.statewise[response.statewise.size-1].lastupdatedtime

        val localInstance = IndiaCachedEntity(100,active.toString(),response.cases_time_series[response.cases_time_series.size-1].totalconfirmed,
                response.cases_time_series[response.cases_time_series.size-1].totaldeceased,
                response.cases_time_series[response.cases_time_series.size-1].totalrecovered, response.statewise[response.statewise.size-1].lastupdatedtime
        )
        mViewModel1.upsertIndiaData(localInstance)
        setUpPieChart(view,chart)

    }


    private fun setUpPieChart(view: View,chart : chartData)
    {
        view.piechart.addPieSlice(
                PieModel(
                        "Total",
                        chart.total.toFloat(), Color.parseColor("#ffb259")
                )
        )

        view.piechart.addPieSlice(
                PieModel(
                        "Death",
                        chart.deaths.toFloat(), Color.parseColor("#ff5959")
                )
        )

        view.piechart.addPieSlice(
                PieModel(
                        "Active",
                        chart.active.toFloat(), Color.parseColor("#4cb5ff")
                )
        )

        view.piechart.addPieSlice(
                PieModel(
                        "Recovered",
                        chart.recovered.toFloat(), Color.parseColor("#4cd97b")
                )
        )

        view.piechart.startAnimation()
    }




    private fun setupUI(view: View) {

        view.global.setOnClickListener()
        {
            requireActivity().chip_menu.setItemSelected(R.id.global_menu)
            val frag = GlobalFragment()
            val bundle = Bundle()
            bundle.putBoolean("STOP",true)
            frag.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_layout,frag).commit()
        }
    }

    //Checking Internet Connection
    private fun isNetworkConnected(): Boolean {
        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }


    // TODO: 23-09-2020 Caching implementations UI
    private fun setLocalIndiaDetails(local : IndiaCachedEntity, view: View) {
        view.total_cases.text = ConvertNumber.formatValue(local.confirmed.toDouble())
        val active = local.active
        view.active_cases.text = ConvertNumber.formatValue(active.toDouble())
        view.recovered_cases.text =ConvertNumber.formatValue( local.recovered.toDouble())
        view.death_cases.text = ConvertNumber.formatValue(local.deaths.toDouble())

        val chart = chartData( local.confirmed,
                active.toString(),local.recovered,
                local.deaths)

        view.last_updated.text = local.lastupdatedtime


        setUpPieChart(view,chart)

    }


}