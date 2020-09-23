package com.androdude.covid19ui.UI


import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.androdude.covid19_appupdate.db.ModelClass.India.IndiaResponse
import com.androdude.covid19_appupdate.db.ModelClass.Total.TotalResponse
import com.androdude.covid19_appupdate.db.ModelClass.WordWide.WorldWideResponse
import com.androdude.covid19_appupdate.db.ViewModel.IndiaDataViewModel
import com.androdude.covid19_appupdate.db.ViewModel.TotalDataViewModel
import com.androdude.covid19_appupdate.db.ViewModel.WorldWideDataViewModel
import com.androdude.covid19ui.R
import com.androdude.covid19ui.UI.Utils.ConvertNumber
import com.androdude.covid19ui.UI.Utils.DialogBox
import com.androdude.covid19ui.UI.Utils.NoInternetDialog
import com.androdude.covid19ui.db.local_database.entites.GlobalCachedEntity
import com.androdude.covid19ui.helper.checkInternet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_global.view.*
import kotlinx.android.synthetic.main.fragment_india.*
import kotlinx.android.synthetic.main.fragment_india.view.*
import kotlinx.android.synthetic.main.fragment_india.view.piechart
import org.eazegraph.lib.models.PieModel
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class GlobalFragment : Fragment(R.layout.fragment_global) {


    //View models
    private lateinit var mViewModel3: TotalDataViewModel
    private lateinit var localDBModel: TotalDataViewModel

    //Response Objects
    private lateinit var response3: LiveData<Response<TotalResponse>>
    private lateinit var WorldResposeData: TotalResponse

    //Dialogbox objects
    private lateinit var loadingDialog: DialogBox
    private lateinit var noInternetDialog : NoInternetDialog






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        goToIndiaFrag(view)
        view.global_frag_layout.visibility=View.GONE

        //Initialization
        loadingDialog = DialogBox(requireActivity())
        noInternetDialog = NoInternetDialog(requireActivity())
        loadingDialog.buildDialog()
        noInternetDialog.buildDialog()
        loadingDialog.show()
        localDBModel = ViewModelProviders.of(this).get(TotalDataViewModel::class.java)
        mViewModel3 = ViewModelProvider(this).get(TotalDataViewModel::class.java)


        if (arguments != null) {
            if (requireArguments().getBoolean("STOP")) {
                loadingDialog.stop()
            }
        }




        if(isNetworkConnected())
        {
            response3 = mViewModel3.getTotalDetails()
            response3.observe(requireActivity(), Observer {
                WorldResposeData = it.body()!!
                view.global_frag_layout.visibility=View.GONE
                view.global_frag_layout.visibility=View.VISIBLE
                setWorldDetails(WorldResposeData, view)
            })
        }
        else
        {
            val handler = Handler()
            handler.postDelayed(Runnable {
                loadingDialog.stop()
                noInternetDialog.show()
            },2500)

        }








    }




    private fun setWorldDetails(response: TotalResponse, view: View) {
        view.global_total_cases.text = ConvertNumber.formatValue(response.cases.toDouble())
        view.global_active_cases.text = ConvertNumber.formatValue(response.active.toDouble())
        view.global_recovered_cases.text = ConvertNumber.formatValue(response.recovered.toDouble())
        view.global_death_cases.text = ConvertNumber.formatValue(response.deaths.toDouble())
        view.global_critical_cases.text = ConvertNumber.formatValue(response.critical.toDouble())
        val chart = chartData(response.cases.toString(),
                response.active.toString(),
                response.recovered.toString(), response.deaths.toString())

        val simple: DateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z")
        val lastUpdated = simple.format(Date(response.updated)).split('+')
        view.global_last_updated.text = lastUpdated[0]
        loadingDialog.stop()

//        val localInstance = GlobalCachedEntity(101,response.cases,response.active,response.recovered,response.critical,response.deaths)
//        localDBModel.upsertGlobalData(localInstance)
        setUpPieChart(view, chart)
    }

    private fun setUpPieChart(view: View, chart: chartData) {
        view.global_piechart.addPieSlice(
                PieModel(
                        "Total",
                        chart.total.toFloat(), Color.parseColor("#ffb259")
                )
        )

        view.global_piechart.addPieSlice(
                PieModel(
                        "Death",
                        chart.deaths.toFloat(), Color.parseColor("#ff5959")
                )
        )

        view.global_piechart.addPieSlice(
                PieModel(
                        "Active",
                        chart.active.toFloat(), Color.parseColor("#4cb5ff")
                )
        )

        view.global_piechart.addPieSlice(
                PieModel(
                        "Recovered",
                        chart.recovered.toFloat(), Color.parseColor("#4cd97b")
                )
        )

        view.global_piechart.startAnimation()
    }

    private fun goToIndiaFrag(view: View) {


        view.global_india.setOnClickListener()
        {
            requireActivity().chip_menu.setItemSelected(R.id.india_menu)
            val frag = IndiaFragment()
            val bundle = Bundle()
            bundle.putBoolean("STOP", true)
            frag.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_layout, frag).commit()

        }


    }

    //Checking Internet Connection
    private fun isNetworkConnected(): Boolean {
        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    // TODO: 23-09-2020 Caching implementations UI
    private fun setLocalWorldDetails(response: GlobalCachedEntity, view: View) {
        view.global_total_cases.text = ConvertNumber.formatValue(response.cases.toDouble())
        view.global_active_cases.text = ConvertNumber.formatValue(response.active.toDouble())
        view.global_recovered_cases.text = ConvertNumber.formatValue(response.recovered.toDouble())
        view.global_death_cases.text = ConvertNumber.formatValue(response.deaths.toDouble())

        val chart = chartData(response.cases.toString(),
                response.active.toString(),
                response.recovered.toString(), response.deaths.toString())
        loadingDialog.stop()
        setUpPieChart(view, chart)
    }






}