package com.example.flipkart.roomDb

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productTable")
data class ProductModel(
    @PrimaryKey
    @NonNull
    val prodId : String ,

    @ColumnInfo(name = "productName")
    val productName : String? = "" ,

    @ColumnInfo(name = "productImage")
    val productImage : String? = "" ,

    @ColumnInfo(name = "productSp")
    val productSp : String? = "" ,
)
