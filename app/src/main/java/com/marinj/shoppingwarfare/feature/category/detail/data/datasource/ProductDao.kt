package com.marinj.shoppingwarfare.feature.category.detail.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.marinj.shoppingwarfare.feature.category.detail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.category.detail.data.model.LocalProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Transaction
    @Query("SELECT * FROM localCategory WHERE categoryId == :categoryId")
    fun observeProductsForGivenCategoryId(categoryId: String): Flow<List<LocalCategoryProducts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProduct(entity: LocalProduct): Long

    @Query("DELETE FROM localProduct WHERE productId == :productId")
    suspend fun deleteProductById(productId: String)
}
