package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ObserveCategoriesTest {

    private val categoryRepository: CategoryRepository = mockk()

    private lateinit var sut: ObserveCategories

    @Before
    fun setUp() {
        sut = ObserveCategories(
            categoryRepository,
        )
    }

    @Test
    fun `invoke should return result from categoryRepository observeCategories`() = runTest {
        val categories = listOf(mockk<Category>())
        val repositoryFlow = flow { emit(categories) }
        coEvery {
            categoryRepository.observeCategories()
        } coAnswers { repositoryFlow }

        sut().test {
            assertThat(awaitItem()).isEqualTo(categories)
            awaitComplete()
        }
    }
}
