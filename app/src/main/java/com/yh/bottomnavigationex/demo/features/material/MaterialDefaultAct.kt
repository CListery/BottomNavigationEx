package com.yh.bottomnavigationex.demo.features.material

import android.os.Bundle
import com.yh.appbasic.ui.ViewBindingActivity
import com.yh.bottomnavigationex.demo.databinding.ActMaterialDefaultBinding

class MaterialDefaultAct : ViewBindingActivity<ActMaterialDefaultBinding>() {
    override fun binderCreator(savedInstanceState: Bundle?) = ActMaterialDefaultBinding.inflate(layoutInflater)

    override fun ActMaterialDefaultBinding.onInit(
        savedInstanceState: Bundle?
    ) {
    }

}