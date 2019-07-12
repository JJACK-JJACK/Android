package jjackjjack.sopt.com.jjackjjack.utillity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.layout_toast.view.*
import kotlin.properties.Delegates

class ColorToast(mContext: Context?, txt: String) {
    private var toast: Toast by Delegates.notNull()

    init{
        mContext?.let{
            toast = Toast.makeText(mContext, txt,Toast.LENGTH_SHORT)

            val layout: View = LayoutInflater.from(mContext).inflate(R.layout.layout_toast, null, false)
            val textView: TextView = layout.findViewById(R.id.tv_toast_text)
            textView.includeFontPadding = false
            textView.setText(txt)

            toast.view = layout
            toast.setMargin(0.0f, 0.1f)
            toast.show()

            //val toastView = toast.view

        }
    }

}
