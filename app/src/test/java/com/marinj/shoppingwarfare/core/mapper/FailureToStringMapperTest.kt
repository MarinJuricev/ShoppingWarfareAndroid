package com.marinj.shoppingwarfare.core.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import org.junit.Before
import org.junit.Test

class FailureToStringMapperTest {

    private lateinit var sut: FailureToStringMapper

    @Before
    fun setUp() {
        sut = FailureToStringMapper()
    }

    @Test
    fun `map should return provided errorMessage when failure is of type ErrorMessage`() {
        val errorMessage = "errorMessage"
        val origin = Failure.ErrorMessage(errorMessage)

        val actualResult = sut.map(origin)

        assertThat(actualResult).isEqualTo(errorMessage)
    }

    @Test
    fun `map should return generic error when failure is not of type ErrorMessage`() {
        val origin = Failure.Unknown

        val actualResult = sut.map(origin)
        val expectedResult = "Unknown Error Occurred, please try again later"

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
