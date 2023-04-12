package com.example.flipkart.model

data class AddProductModel(
    val productName : String? = "",
    val productDesc : String? = "",
    val productCoverImg : String? = "",
    val productCategory : String? = "",
    val productId : String? = "",
    val productMrp : String? = "",
    val productSp : String? = "",
    val productImages : ArrayList<String> = ArrayList()
)
