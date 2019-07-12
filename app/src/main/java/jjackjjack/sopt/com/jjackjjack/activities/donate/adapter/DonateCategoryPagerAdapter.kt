package jjackjjack.sopt.com.jjackjjack.activities.donate.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.*
import jjackjjack.sopt.com.jjackjjack.utillity.Constants

class DonateCategoryPagerAdapter : FragmentPagerAdapter {

    constructor(fragmentPagerAdapter: FragmentManager) : super(fragmentPagerAdapter){
    }
    companion object { //싱글톤 design pattern ( 기존에 생성된 객체 재사용, 그때그때마다 새롭게 생성 방지 )

        private var animalFragment: AnimalFragment? = null
        private var childFragment: ChildFragment? = null
        private var disabledFragment: DisabledFragment? = null
        private var elderFragment: ElderFragment? = null
        private var emergencyFragment: EmergencyFragment? = null
        private var environmentFragment: EnvironmentFragment? = null

        @Synchronized
        fun getAnimalFragment() : AnimalFragment{
            if(animalFragment == null) animalFragment = AnimalFragment()
            return animalFragment!!
        }

        @Synchronized
        fun getChildFragment() : ChildFragment{
            if(childFragment == null) childFragment = ChildFragment()
            return childFragment!!
        }

        @Synchronized
        fun getElderFragment() : ElderFragment{
            if(elderFragment == null) elderFragment = ElderFragment()
            return elderFragment!!
        }

        @Synchronized
        fun getEmergencyFragment() : EmergencyFragment{
            if(emergencyFragment == null) emergencyFragment = EmergencyFragment()
            return emergencyFragment!!
        }

        @Synchronized
        fun getDisabledFragment() : DisabledFragment{
            if(disabledFragment == null) disabledFragment = DisabledFragment()
            return disabledFragment!!
        }

        @Synchronized
        fun getEnvironmentFragment() : EnvironmentFragment{
            if(environmentFragment == null) environmentFragment = EnvironmentFragment()
            return environmentFragment!!
        }
    }

    override fun getItem(p0: Int): Fragment? {
        return when(p0){ //몇번째 인덱스냐에 따라서 다른 fragment 반환
            Constants.FRAGMENT_CHILD-> getChildFragment()
            Constants.FRAGMENT_ELDER-> getElderFragment()
            Constants.FRAGMENT_ANIMAL-> getAnimalFragment()
            Constants.FRAGMENT_DISABLE-> getDisabledFragment()
            Constants.FRAGMENT_ENVIRONMENT-> getEnvironmentFragment()
            Constants.FRAGMENT_EMERGENCY-> getEmergencyFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return 6
    }

}