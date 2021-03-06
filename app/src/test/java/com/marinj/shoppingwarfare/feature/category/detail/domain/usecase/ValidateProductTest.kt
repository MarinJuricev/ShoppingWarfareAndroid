package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import org.junit.Before
import org.junit.Test

class ValidateProductTest {

    private lateinit var sut: ValidateProduct

    @Before
    fun setUp() {
        sut = ValidateProduct()
    }

    @Test
    fun `invoke should return Left when title is null`() {
        val title = null

        val actualResult = sut(title)
        val expectedResult = ErrorMessage("Title can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return Left when title is empty`() {
        val title = ""

        val actualResult = sut(title)
        val expectedResult = ErrorMessage("Title can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return Right when all validation passes`() {
        val title = "title"

        val actualResult = sut(title)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
