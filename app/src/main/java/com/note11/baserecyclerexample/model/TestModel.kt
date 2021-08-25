package com.note11.baserecyclerexample.model

data class TestModel(
    val indexNumber: String,
    val textString: String = "${indexNumber}번째"
)
