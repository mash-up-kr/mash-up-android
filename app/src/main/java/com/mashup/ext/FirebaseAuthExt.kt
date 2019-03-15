package com.mashup.ext

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable
import io.reactivex.Single

inline fun Task<AuthResult>.toSingle(): Single<FirebaseUser> = Single.create { emitter ->
    addOnSuccessListener { emitter.onSuccess(it.user) }
    addOnFailureListener(emitter::onError)
}

inline fun Task<AuthResult>.toObservable(): Observable<FirebaseUser> = Observable.create { emitter ->
    addOnSuccessListener { emitter.onNext(it.user) }
    addOnFailureListener(emitter::onError)
    addOnCompleteListener { emitter.onComplete() }
}