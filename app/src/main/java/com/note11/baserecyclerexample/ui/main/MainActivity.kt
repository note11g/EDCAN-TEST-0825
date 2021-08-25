package com.note11.baserecyclerexample.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.note11.baserecyclerexample.BR
import com.note11.baserecyclerexample.R
import com.note11.baserecyclerexample.databinding.ActivityMainBinding
import com.note11.baserecyclerexample.databinding.ItemMainBinding
import com.note11.baserecyclerexample.model.TestModel
import com.note11.baserecyclerexample.network.TestRepository
import com.note11.baserecyclerexample.ui.base.BaseRcv

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val testList: ObservableArrayList<TestModel> = ObservableArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initActivity()
        getItems()
    }

    private fun initActivity() = with(binding) {
        lifecycleOwner = this@MainActivity
        activity = this@MainActivity

        val adapter = TestAdapter{
            //onClick
        }

//            object : BaseRcv.Adapter<TestModel, ItemMainBinding>(
//                itemSame = { old, new -> old.indexNumber == new.indexNumber },
//                itemLayoutId = R.layout.item_main,
//                itemBindingID = BR.test,
//                onItemClick = {
//                    Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
//                }
//            ) {}

        recyclerMain.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerMain.adapter = adapter
    }

    fun addItem() {
        val model = TestModel("${testList.size + 1}")
        testList.add(model)
        TestRepository.uploadTest(model) {
            Toast.makeText(this, "${it.id}에 업로드 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getItems() {
        TestRepository.downloadTest { q ->
            val getList = q.documents
            testList.clear()

            getList.map {
                testList.add(TestModel(it["indexNumber"] as String, it["textString"] as String))
            }
        }
    }
}

object MainBindingConversion { //리사이클러뷰 필수!
    @BindingAdapter("bindItems")
    @JvmStatic
    fun bindItems(rcv: RecyclerView, list: List<TestModel>) {
        (rcv.adapter as TestAdapter).run {
            submitList(list)
            notifyDataSetChanged()
        }
    }
}