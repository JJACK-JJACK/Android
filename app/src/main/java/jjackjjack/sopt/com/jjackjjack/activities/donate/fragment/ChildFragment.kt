package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.list.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonateSortedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateSortedListResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.fragment_child_category.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChildFragment : Fragment(), View.OnClickListener{

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    val dataList: ArrayList<DonateInfo> by lazy {
        ArrayList<DonateInfo>()
    }

    val dataList_DonateInfo: ArrayList<DonateInfo> by lazy{
        ArrayList<DonateInfo>()
    }

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var CategoryId = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialUI()

    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.tv_sorted_recent->{
                getDonateSortedListResponse(0)
                updateTextColor(tv_sorted_recent)
            }
            R.id.tv_sorted_popular->{
                getDonateSortedListResponse(1)
                updateTextColor(tv_sorted_popular)
            }
            R.id.tv_sorted_unpopular->{
                getDonateSortedListResponse(2)
                updateTextColor(tv_sorted_unpopular)
            }
            else->{

            }
        }
    }

    private fun initialUI(){
        donateListRecyclerViewAdapter =
            DonateListRecyclerViewAdapter(context!!, dataList, false)
        rv_recent_category.adapter = donateListRecyclerViewAdapter
        rv_recent_category.layoutManager = LinearLayoutManager(context!!)

        clearDataList()

        tv_sorted_recent.setOnClickListener(this)
        tv_sorted_popular.setOnClickListener(this)
        tv_sorted_unpopular.setOnClickListener(this)

        updateTextColor(tv_sorted_recent)
    }

    private var curTextView: TextView? = null
    private fun updateTextColor(textView: TextView){
        activity?.let{
            curTextView?.setTextColor(ContextCompat.getColor(it.applicationContext, R.color.grayB))
            curTextView = textView

            textView.setTextColor(ContextCompat.getColor(it.applicationContext, R.color.darkGrayA))
        }
    }

    private fun getDonateSortedListResponse(filterId: Int){
        clearDataList()

        val getDonateSortedListResponse = networkService.getDonateSortedListResponse(CategoryId, filterId)
        getDonateSortedListResponse.enqueue(object : Callback<GetDonateSortedListResponse> {
            override fun onFailure(call: Call<GetDonateSortedListResponse>, t: Throwable) {
                progress_bar.visibility = View.GONE
                ColorToast(activity?.applicationContext, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(
                call: Call<GetDonateSortedListResponse>,
                response: Response<GetDonateSortedListResponse>
            ) {
                progress_bar.visibility = View.GONE
                if(response.isSuccessful){
                    if(response.body()!!.status == Secret.NETWORK_LIST_SUCCESS){
                        val temp: ArrayList<DonateSortedData> = response.body()!!.data
                        for(i in 0 until temp.size) {
                            if (temp[i].state == 0) { //진행중인 아이템만 추가
                                dataList_DonateInfo.add(temp[i].toDonateInfo())
                            }
                        }
                        updateDonateList(dataList_DonateInfo)
                    }
                    else{
                        ColorToast(activity?.applicationContext, response.body()!!.message)
                    }

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        clearDataList()
        getDonateSortedListResponse(Constants.TAB_RECENT)
    }

    private fun updateDonateList(list: ArrayList<DonateInfo>){
        dataList.clear()
        dataList.addAll(list)
        donateListRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun clearDataList(){
        dataList_DonateInfo.clear()
        dataList.clear()
        donateListRecyclerViewAdapter.notifyDataSetChanged()
    }


}
