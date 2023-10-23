package com.hiconsy.app.sdijcompat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import com.hiconsy.app.hds.toast.CommonToast

class MainActivity : AppCompatActivity() {
    private val tv : AppCompatTextView by lazy { findViewById(R.id.tvtv) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.setOnClickListener {
            CommonToast.createToast(this, "sdfsdf").show()
        }
    }
}