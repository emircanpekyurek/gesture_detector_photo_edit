package com.pekyurek.emircan.presentation.core.data.repository

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class RepositoryImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repositoryImpl: RepositoryImpl

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `candidatesOverlay() success_case_requiredConnection`() {
        runBlocking {
            val result = repositoryImpl.candidatesOverlay().toList()

            assert(result[0] is ResultStatus.Loading)
            assert((result[0] as ResultStatus.Loading).show)
            assert((result[1] is ResultStatus.Success))
            assert((result[1] as ResultStatus.Success).data.isNotEmpty())
            assert(result[2] is ResultStatus.Loading)
            assert((result[2] as ResultStatus.Loading).show.not())
        }
    }
}
