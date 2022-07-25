package csv.brownbag.mvvm.basic.model

import android.util.Patterns


class User(private val mEmail: String?, private val mPassword: String?) {
    val email: String
        get() = mEmail ?: ""
    val password: String
        get() {
            return mPassword ?: ""
        }

    fun isEmailValid(): Boolean {
        mEmail?.let {
            return Patterns.EMAIL_ADDRESS.matcher(it).matches()
        }
        return false
    }

    fun isPasswordLengthGreaterThan5(): Boolean {
        mPassword?.let {
            return mPassword.length > 5
        }
        return false
    }

}