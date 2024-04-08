package com.zezekalo.pixabaytest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.zezekalo.pixabaytest.actions.RecyclerViewChildActions
import com.zezekalo.pixabaytest.presentation.entity.UiPicture
import com.zezekalo.pixabaytest.presentation.ui.fragment.PicturesFragment
import com.zezekalo.pixabaytest.presentation.viewmodel.PicturesUiState
import com.zezekalo.pixabaytest.presentation.viewmodel.PicturesViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
class PicturesFragmentAndroidTest {

    @BindValue
    @JvmField
    val mockedViewModel: PicturesViewModel = mockk<PicturesViewModel>(relaxed = true, relaxUnitFun = true)

    private val testCoroutineScope = TestScope()

    private val uiState = MutableStateFlow(PicturesUiState())
    private val pictures: Flow<List<UiPicture>>
        get() = uiState.map { it.pictures }
    private val query = MutableStateFlow<String>("")

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        every { mockedViewModel.uiState } returns uiState
        every { mockedViewModel.query } returns query

        hiltRule.inject()
    }

    @Test
    fun test_WhenEmptyList()  = testCoroutineScope.runTest {
        launchFragmentInHiltContainer<PicturesFragment>()
        uiState.emit(PicturesUiState(pictures = emptyList(), shouldShowEmpty = true, errorMessage = null, showDialog = null, loading = false))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.queryResult))
            .check(matches(isDisplayed()))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.error))
            .check(matches(not(isDisplayed())))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.spinner))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_WhenLoading()  = testCoroutineScope.runTest {
        launchFragmentInHiltContainer<PicturesFragment>()
        uiState.emit(PicturesUiState(pictures = emptyList(), shouldShowEmpty = false, errorMessage = null, showDialog = null, loading = true))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.queryResult))
            .check(matches(not(isDisplayed())))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.error))
            .check(matches(not(isDisplayed())))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.spinner))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_WhenGettingError()  = testCoroutineScope.runTest {
        launchFragmentInHiltContainer<PicturesFragment>()
        uiState.emit(PicturesUiState(pictures = emptyList(), shouldShowEmpty = false, errorMessage = "Error", showDialog = null, loading = false))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.queryResult))
            .check(matches(not(isDisplayed())))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.error))
            .check(matches(isDisplayed()))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.spinner))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_WhenPictureListIsNotEmpty()  = testCoroutineScope.runTest {
        launchFragmentInHiltContainer<PicturesFragment>()
        uiState.emit(PicturesUiState(pictures = testPictures, shouldShowEmpty = true, errorMessage = null, showDialog = null, loading = false))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.queryResult))
            .check(matches(not(isDisplayed())))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.error))
            .check(matches(not(isDisplayed())))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.spinner))
            .check(matches(not(isDisplayed())))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.picturesRv))
            .check(matches(
                RecyclerViewChildActions.childOfViewAtPositionWithMatcher(
                    com.zezekalo.pixabaytest.presentation.R.id.user, 0, withText("User: User_1")
                )
            ))

        onView(withId(com.zezekalo.pixabaytest.presentation.R.id.picturesRv))
            .check(matches(
                RecyclerViewChildActions.childOfViewAtPositionWithMatcher(
                    com.zezekalo.pixabaytest.presentation.R.id.user, 1, withText("User: User_2")
                )
            ))

    }

    companion object {

        private val testPictures = listOf(
            UiPicture(
                id = 1,
                user = "User_1",
                tags = listOf("tag1", "tag2", "tag3"),
                thumbnailUrl = "https://cdn.pixabay.com/photo/2013/10/15/09/12/flower-195893_150.jpg",
            ),
            UiPicture(
                id = 2,
                user = "User_2",
                tags = listOf("tag3", "tag2", "tag1"),
                thumbnailUrl = "https://cdn.pixabay.com/photo/2013/10/15/09/12/flower-195893_150.jpg",
            )
        )
    }
}