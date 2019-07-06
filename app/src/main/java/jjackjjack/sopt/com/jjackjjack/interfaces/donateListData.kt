package jjackjjack.sopt.com.jjackjjack.interfaces

interface donateListData { //기부 Sort안에 들어가는 리스트 아이템, 기부이력 아이템
    fun getThumbnail():String
    fun getStart():String
    fun getFinish():String
    fun getTitle():String
    fun getCenterName(): String
    fun getState(): Int
    fun getPercentage(): Int

}