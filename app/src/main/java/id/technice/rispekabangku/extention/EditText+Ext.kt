package id.technice.rispekabangku.extention

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.addPasswordToggleIcon(
    showIcon: Drawable,
    hideIcon: Drawable
) {
    val iconSize = com.intuit.sdp.R.dimen._10sdp
    // Set the initial icon to show password
    val icon = showIcon.apply {
        setBounds(0, 0, iconSize,iconSize)
    }
    this.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
    var passwordVisible = false

    // Set a click listener on the icon to toggle password visibility
    this.setOnClickListener {
        passwordVisible = !passwordVisible
        val passwordTransformation = if (passwordVisible) {
            HideReturnsTransformationMethod.getInstance()
        } else {
            PasswordTransformationMethod.getInstance()
        }
        this.transformationMethod = passwordTransformation
        val newIcon = if (passwordVisible) {
            hideIcon.apply {
                setBounds(0, 0, iconSize, iconSize)
            }
        } else {
            showIcon.apply {
                setBounds(0, 0, iconSize, iconSize)
            }
        }
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, newIcon, null)
    }
}