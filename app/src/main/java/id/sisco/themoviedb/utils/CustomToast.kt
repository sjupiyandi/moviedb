package id.sisco.themoviedb.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import id.sisco.themoviedb.R
import kotlinx.android.synthetic.main.custom_toast_layout.view.*

class CustomToast {
    companion object {
        const val GRAVITY_BOTTOM = 80
        private lateinit var layoutInflater: LayoutInflater
        fun succesToast(context: Context, message: String, position: Int) {
            layoutInflater = LayoutInflater.from(context)
            val view = View(context)
            val layout = layoutInflater.inflate(
                R.layout.custom_toast_layout,
                (view).findViewById(R.id.custom_toast_layout)
            )
            val drawable = ContextCompat.getDrawable(context, R.drawable.toast_round_background)
            drawable?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                PorterDuff.Mode.MULTIPLY
            )
            layout.background = drawable
            layout.custom_toast_message.setTextColor(Color.WHITE)
            layout.custom_toast_message.text = message
            val toast = Toast(context.applicationContext)
            toast.duration = Toast.LENGTH_LONG
            if (position == GRAVITY_BOTTOM) {
                toast.setGravity(position, 0, 20)
            } else {
                toast.setGravity(position, 0, 0)
            }
            toast.view = layout //setting the view of custom toast layout
            toast.show()
        }

        fun failureToast(context: Context, message: String, position: Int) {
            layoutInflater = LayoutInflater.from(context)
            val view = View(context)
            val layout = layoutInflater.inflate(
                R.layout.custom_toast_layout,
                (view).findViewById(R.id.custom_toast_layout)
            )
//            layout.custom_toast_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_information))
            val drawable = ContextCompat.getDrawable(context, R.drawable.toast_round_background)
            drawable?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(context, R.color.colorAccent),
                PorterDuff.Mode.MULTIPLY
            )
            layout.background = drawable
            layout.custom_toast_message.setTextColor(Color.WHITE)
            layout.custom_toast_message.text = message
            val toast = Toast(context.applicationContext)
            toast.duration = Toast.LENGTH_LONG
            if (position == GRAVITY_BOTTOM) {
                toast.setGravity(position, 0, 20)
            } else {
                toast.setGravity(position, 0, 0)
            }
            toast.view = layout //setting the view of custom toast layout
            toast.show()
        }
    }
}