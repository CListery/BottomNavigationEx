package com.yh.bottomnavigationex.demo.features.example

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.onClick
import androidx.appcompat.app.AppCompatActivity
import com.yh.appbasic.logger.logD
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
        
        bind.btn.onClick {
            logD("origin height: ${bind.bnve.height}", tag = "bnve")
            logD("realView height: ${bind.bnve.realView.height}", tag = "bnve")
            
            val animator: ObjectAnimator = if(bind.bnve.realView.translationY > 0F) {
                ObjectAnimator.ofFloat(
                    bind.bnve.realView,
                    "translationY",
                    bind.bnve.realView.height.toFloat(),
                    0F
                )
            } else {
                ObjectAnimator.ofFloat(
                    bind.bnve.realView,
                    "translationY",
                    0F,
                    bind.bnve.realView.height.toFloat()
                )
            }
            animator.duration = 600
            animator.start()
        }
    }
}