package id.sisco.themoviedb.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import id.sisco.themoviedb.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Helpers {

    private val EMAIL_ADDRESS_VALIDATE : Pattern = Pattern.compile(
        "[a-zA-Z0-9+._%-]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                ")+"
    )

    fun isEmailValid(email: CharSequence): Boolean {
        return EMAIL_ADDRESS_VALIDATE.matcher(email).matches()
    }

    @JvmStatic
    fun detail(date: String?, time: String?): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
            val year = formatter.format(parser.parse(date))
            return "$time minutes ∙ $year"
        } catch (e: Exception) {
            ""
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view: ImageView?, icon: String?) {
        if (view != null) {
            if (icon != null){
                GlideApp.with(view.context)
                    .load("https://image.tmdb.org/t/p/w500/$icon")
                    .apply(RequestOptions().override(437, 656))
                    .placeholder(R.drawable.ic_person)
                    .into(view)
            }else GlideApp.with(view.context).load(R.drawable.ic_person).apply( RequestOptions().override(437, 656)).placeholder(
                R.drawable.ic_person).into(view)
        }
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun date(date: String?): String {
        return if (!date.isNullOrEmpty()) {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            formatter.format(parser.parse(date))
        } else {
            "-"
        }
    }
}