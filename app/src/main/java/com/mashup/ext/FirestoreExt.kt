package com.mashup.ext

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import io.reactivex.Single

inline fun <reified T> Query.toSingle(): Single<List<T>> = Single.create { emitter ->
    get().addOnSuccessListener {
        emitter.onSuccess(it.toObjects(T::class.java))
    }.addOnFailureListener {
        emitter.onError(it)
    }
}


inline fun <reified T> DocumentReference.toSingle(): Single<T> = Single.create { emitter ->
    get().addOnSuccessListener {
        val item = it.toObject(T::class.java)
        if (item == null) {
            emitter.onError(NullPointerException("item is null"))
        } else {
            emitter.onSuccess(item)
        }
    }.addOnFailureListener {
        emitter.onError(it)
    }
}
