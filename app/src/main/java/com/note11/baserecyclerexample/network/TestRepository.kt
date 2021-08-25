package com.note11.baserecyclerexample.network

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.note11.baserecyclerexample.model.TestModel

object TestRepository {
    private val db = Firebase.firestore.collection("memo")
    private const val TAG = "TestRepository"

    fun uploadTest(model: TestModel, onSucceed: (DocumentReference) -> Unit) {
        db.add(model)
            .addOnSuccessListener(onSucceed)
            .addOnFailureListener {
                Log.e(TAG, "error: $it")
            }
    }

    fun downloadTest(onSucceed: (QuerySnapshot) -> Unit) {
        db.get().addOnSuccessListener(onSucceed)
            .addOnFailureListener {
                Log.e(TAG, "error: $it")
            }
    }
}