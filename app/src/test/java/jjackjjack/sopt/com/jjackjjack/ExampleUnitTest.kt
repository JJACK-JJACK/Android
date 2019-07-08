package jjackjjack.sopt.com.jjackjjack

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val finish = "2019-06-20T00:00:00.000Z"

        var Dday : Int = 0

        var convertedDate_finish = StringBuilder()

        if(finish != null) {
            //val dateInfoList_finish = finish.split("T")
            val today = Calendar.getInstance() //현재 오늘 날짜


            val finishdateFormat = SimpleDateFormat("yyyy-MM-dd").parse(finish.split("T")[0])

            val instance: Calendar = Calendar.getInstance()
            instance.setTime(finishdateFormat)

            val cnt_today: Long = today.timeInMillis / 86400000
            val cnt_instance: Long = today.timeInMillis / 86400000

            val sub: Long = cnt_today - cnt_instance

            Dday = sub.toInt() + 1

            assertEquals(Dday, 19)
        }
    }
}
