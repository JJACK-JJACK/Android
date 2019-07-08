package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.sort

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.list.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonateSortedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateSortedListResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.fragment_recent_category.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentFragment : Fragment(){

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    val dataList : ArrayList<DonateInfo> by lazy{
        ArrayList<DonateInfo>()
    }

    val dataList_DonateInfo : ArrayList<DonateInfo> by lazy {
        ArrayList<DonateInfo>()
    }

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    val CategoryId = 0 //todo 이거 받아오도록

    lateinit var donateInfo : DonateInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_recent_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        var list: ArrayList<DonateInfo> = ArrayList()

//        list.add(
//            DonateInfo(
//                "64", "혜리에게 따듯한 이불을", "솝트", "35", "150.000"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "15", "윤희에게 따듯한 이불을", "솝트", "61", "199.999"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "33", "민진에게 따듯한 이불을", "솝트", "88", "130.000"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "20", "상일에게 따듯한 이불을", "솝트", "10", "222.000"
//            )
//        )
        initialUI()
        getDonateSortedListResponse()

    }

    private fun getDonateSortedListResponse(){
        val getDonateSortedListResponse = networkService.getDonateSortedListResponse(CategoryId, 0)
        getDonateSortedListResponse.enqueue(object : Callback<GetDonateSortedListResponse>{
            override fun onFailure(call: Call<GetDonateSortedListResponse>, t: Throwable) {
                Log.e("Sorted List fail", t.toString())
            }

            override fun onResponse(
                call: Call<GetDonateSortedListResponse>,
                response: Response<GetDonateSortedListResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == Secret.NETWORK_LIST_SUCCESS){
                        toast(response.body()!!.message)
                        val temp: ArrayList<DonateSortedData> = response.body()!!.data
                        for(i in 0 until temp.size){
                            dataList_DonateInfo.add(temp[i].toDonateInfo())
                        }
//                        donateListRecyclerViewAdapter.list = dataList
//                        donateListRecyclerViewAdapter.notifyDataSetChanged()
                        updateDonateList(dataList_DonateInfo)
                    }
                    else{
                        toast(response.body()!!.message)
                    }

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        getDonateSortedListResponse()

    }

    private fun updateDonateList(list: ArrayList<DonateInfo>){
        dataList.clear()
        dataList.addAll(list)
        donateListRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun initialUI(){
        donateListRecyclerViewAdapter =
            DonateListRecyclerViewAdapter(context!!, dataList, false)
        rv_recent_category.adapter = donateListRecyclerViewAdapter
        rv_recent_category.layoutManager = LinearLayoutManager(context!!)
    }

}