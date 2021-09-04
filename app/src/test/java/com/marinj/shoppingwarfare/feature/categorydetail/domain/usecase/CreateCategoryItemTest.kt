package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val UUID = "id"
private const val CATEGORY_ITEM_TITLE = "title"

@ExperimentalCoroutinesApi
class CreateCategoryItemTest {

    private val validateCategoryItem: ValidateCategoryItem = mockk()
    private val uuidGenerator: () -> String = mockk()
    private val categoryDetailRepository: CategoryDetailRepository = mockk()

    private lateinit var sut: CreateCategoryItem

    @Before
    fun setUp() {
        every { uuidGenerator() } answers { UUID }

        sut = CreateCategoryItem(
            validateCategoryItem,
            uuidGenerator,
            categoryDetailRepository,
        )
    }

    @Test
    fun `invoke should return Left when validateCategoryItem returns Left`() = runBlockingTest {
        val left = Failure.Unknown.buildLeft()
        coEvery {
            validateCategoryItem(CATEGORY_ITEM_TITLE)
        } coAnswers { left }

        val actualResult = sut(CATEGORY_ITEM_TITLE)

        assertThat(actualResult).isEqualTo(left)
    }

    @Test
    fun `invoke should return Left when validateCategoryItem returns Right and CategoryDetailRepository returns Left`() =
        runBlockingTest {
            val repositoryLeft = Failure.Unknown.buildLeft()
            val validatorRight = Unit.buildRight()
            val categoryItem = CategoryItem(UUID, CATEGORY_ITEM_TITLE)
            coEvery {
                validateCategoryItem(CATEGORY_ITEM_TITLE)
            } coAnswers { validatorRight }
            coEvery {
                categoryDetailRepository.upsertCategoryItem(categoryItem)
            } coAnswers { repositoryLeft }

            val actualResult = sut(CATEGORY_ITEM_TITLE)

            assertThat(actualResult).isEqualTo(repositoryLeft)
        }

    @Test
    fun `invoke should return Right when validateCategoryItem returns Right and CategoryDetailRepository returns Right`() =
        runBlockingTest {
            val repositoryRight = Unit.buildRight()
            val validatorRight = Unit.buildRight()
            val categoryItem = CategoryItem(UUID, CATEGORY_ITEM_TITLE)
            coEvery {
                validateCategoryItem(CATEGORY_ITEM_TITLE)
            } coAnswers { validatorRight }
            coEvery {
                categoryDetailRepository.upsertCategoryItem(categoryItem)
            } coAnswers { repositoryRight }

            val actualResult = sut(CATEGORY_ITEM_TITLE)

            assertThat(actualResult).isEqualTo(repositoryRight)
        }
}
