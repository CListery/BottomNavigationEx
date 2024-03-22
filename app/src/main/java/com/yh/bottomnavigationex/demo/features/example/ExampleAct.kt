package com.yh.bottomnavigationex.demo.features.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yh.bottomnavigationex.demo.databinding.ActExampleBinding

class ExampleAct : AppCompatActivity() {
    
    private val bind: ActExampleBinding by lazy { ActExampleBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(bind.root)
        
        bind.bnve.apply {
//            enableAnimation(true)
            setTextSize(12F)
            enableLabelVisibility(false)
        }
         
        
    }
}