package com.note11.baserecyclerexample.ui.main

import com.note11.baserecyclerexample.BR
import com.note11.baserecyclerexample.R
import com.note11.baserecyclerexample.databinding.ItemMainBinding
import com.note11.baserecyclerexample.model.TestModel
import com.note11.baserecyclerexample.ui.base.BaseRcv

class TestAdapter(
    onClick: (TestModel) -> Unit
) :BaseRcv.Adapter<TestModel, ItemMainBinding>(
    itemSame = { old, new -> old.indexNumber == new.indexNumber },
    itemLayoutId = R.layout.item_main,
    itemBindingID = BR.test,
    onItemClick = onClick
)