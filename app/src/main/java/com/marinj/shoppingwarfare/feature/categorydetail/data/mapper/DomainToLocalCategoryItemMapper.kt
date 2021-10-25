package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import javax.inject.Inject

class DomainToLocalCategoryItemMapper @Inject constructor() {

    fun map(origin: Product): LocalProduct {
        return with(origin) {
            LocalProduct(
                productId = id,
                categoryProductId = categoryId,
                categoryName = categoryName,
                name = name,
            )
        }
    }
}
