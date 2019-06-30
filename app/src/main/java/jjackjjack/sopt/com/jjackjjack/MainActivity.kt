package jjackjjack.sopt.com.jjackjjack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initalUI()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initalUI(){
        btn_hambuger.setOnClickListener {
            if(!ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.openDrawer(Gravity.END)
            }
        }

        btn_cancle.setOnClickListener {
            if(ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.closeDrawer(Gravity.END)
            }
        }
    }

    override fun onBackPressed() {
        if(ly_drawer.isDrawerOpen(Gravity.END)){
            ly_drawer.closeDrawer(Gravity.END)
        }
        else{
            super.onBackPressed()
        }
    }
}
