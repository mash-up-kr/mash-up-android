package com.mashup.app.auth

import android.widget.Toast
import com.airbnb.mvrx.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mashup.ext.toObservable
import com.mashup.ext.toSingle
import com.mashup.model.User
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SignInViewModel(initialState: SignInState): BaseMvRxViewModel<SignInState>(initialState) {

    fun signIn(email: String, pw: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, pw)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .concatMap {
                FirebaseFirestore.getInstance().document("users/$email")
                    .toSingle<User>()
                    .toObservable()
            }
            .execute { copy(signInRequest = it) }
    }

    fun signIn(user: FirebaseUser) {
        FirebaseFirestore.getInstance().document("users/${user.email}")
            .toSingle<User>()
            .toObservable()
            .execute { copy(signInRequest = it) }
    }

    companion object: MvRxViewModelFactory<SignInViewModel, SignInState> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: SignInState): SignInViewModel? {
            return SignInViewModel(state)
        }

        @JvmStatic
        override fun initialState(viewModelContext: ViewModelContext): SignInState? {
            return SignInState()
        }
    }
}

data class SignInState(
    val signInRequest: Async<User> = Uninitialized
): MvRxState