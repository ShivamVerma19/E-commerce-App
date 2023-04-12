package com.example.flipkart.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product : ProductModel)

    @Delete
    suspend fun deleteProduct(product : ProductModel)

    @Query("SELECT * FROM productTable" )
    fun getAllProducts() : LiveData<List<ProductModel>>

    @Query("SELECT * FROM productTable where prodId = :id")
    fun isExist(id : String) : ProductModel
}