package csv.brownbag.mvvm.basic.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import csv.brownbag.mvvm.basic.model.User


class LoginViewModel: ViewModel() {

    var errorPassword = MutableLiveData<String>()
    var errorEmail = MutableLiveData<String>()

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var busy = MutableLiveData<Int>(8)


    private var userMutableLiveData: MutableLiveData<User>? = null

    fun getUser(): LiveData<User>? {
        if (userMutableLiveData == null) {
            userMutableLiveData = MutableLiveData()
        }
        return userMutableLiveData
    }


    fun onLoginClicked() {
        busy.value = 0 //View.VISIBLE
        errorEmail.value = null
        errorPassword.value = null

        Handler().postDelayed({
            val user = User(
                email.value,
                password.value
            )
            if (!user.isEmailValid()) {
                errorEmail.value = "Enter a valid email address"
            } else {
                errorEmail.value = null
            }
            if (!user.isPasswordLengthGreaterThan5()) {
                errorPassword.value = "Password Length should be greater than 5"
            } else {
                errorPassword.value = null
            }
            userMutableLiveData!!.value = user
            busy.value = 8 //8 == View.GONE
        }, 3000)
    }

}