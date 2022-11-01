package com.example.lostdog.ui.fragments

import androidx.fragment.app.Fragment
import com.example.lostdog.MainActivity
import com.example.lostdog.R
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.utilities.AUTH
import com.example.lostdog.utilities.replaceActivity
import com.example.lostdog.utilities.replaceFragment
import com.example.lostdog.utilities.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import java.util.concurrent.TimeUnit

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onStart() {
        super.onStart()

        mCallback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Добро пожаловать")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else
                        showToast(task.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterVerifyCodeFragment(mPhoneNumber, id))
            }
        }

        register_button_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()) {
            showToast("Введите номер телефона")
        } else {
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = register_input_phone_number.text.toString()

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(mPhoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(activity as RegisterActivity)
            .setCallbacks(mCallback).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}