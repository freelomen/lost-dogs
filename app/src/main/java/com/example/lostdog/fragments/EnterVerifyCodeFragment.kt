package com.example.lostdog.fragments

import androidx.fragment.app.Fragment
import com.example.lostdog.MainActivity
import com.example.lostdog.R
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.utilities.AUTH
import com.example.lostdog.utilities.AppTextWatcher
import com.example.lostdog.utilities.replaceActivity
import com.example.lostdog.utilities.showToast
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_verify_code.*

class EnterVerifyCodeFragment(val phoneNumber: String, val id: String):
    Fragment(R.layout.fragment_enter_verify_code) {

    override fun onStart() {
        super.onStart()

        enter_input_verify_code.addTextChangedListener(AppTextWatcher{
            val string : String = enter_input_verify_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = enter_input_verify_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else
                showToast(task.exception?.message.toString())
        }
    }
}