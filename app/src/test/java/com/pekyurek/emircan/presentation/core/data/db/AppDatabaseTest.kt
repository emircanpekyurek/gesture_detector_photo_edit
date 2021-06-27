package com.pekyurek.emircan.presentation.core.data.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pekyurek.emircan.presentation.core.data.db.dao.OverlayDao
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import com.pekyurek.emircan.presentation.core.extensions.imageUrlToPath
import com.pekyurek.emircan.presentation.core.extensions.previewImageUrlToLocalPath
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import javax.inject.Inject


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class AppDatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var appDatabase: AppDatabase

    private val context: Context = ApplicationProvider.getApplicationContext()

    lateinit var overlayDao: OverlayDao


    val dummyOverlay1 = Overlay(1, "overlayName1", "previewUrl1", "imageUrl1")
    val dummyOverlay2 = Overlay(2, "overlayName2", "previewUrl2", "imageUrl2")
    val dummyOverlay3 = Overlay(3, "overlayName3", "previewUrl3", "imageUrl3")
    val dummyOverlay4 = Overlay(4, "overlayName4", "previewUrl4", "imageUrl4")

    @Before
    fun setUp() {
        hiltRule.inject()
        overlayDao = appDatabase.overlayDao()
    }

    @Test
    fun _insert() {
        runBlocking {
            overlayDao._insert(dummyOverlay1)

            val allList = overlayDao.getAllOverlay().first()
            assert(allList.contains(dummyOverlay1))
        }
    }

    @Test
    fun insertOverlayList() {
        runBlocking {
            overlayDao.insertOverlayList(context, listOf(dummyOverlay2))
            val allList = overlayDao.getAllOverlay().first()

            val previewPath = dummyOverlay2.previewImageUrlToLocalPath(context)
            val imagePath = dummyOverlay2.imageUrlToPath(context)

            val databaseOverlay = Overlay(
                dummyOverlay2.overlayId,
                dummyOverlay2.overlayName,
                previewPath,
                imagePath
            )
            assert(allList.contains(databaseOverlay))
        }
    }

    @Test
    fun deleteAllOverlay() {
        File(dummyOverlay3.overlayPreviewIconUrl).createNewFile()
        File(dummyOverlay3.overlayUrl).createNewFile()
        File(dummyOverlay4.overlayPreviewIconUrl).createNewFile()
        File(dummyOverlay4.overlayUrl).createNewFile()


        assert(File(dummyOverlay3.overlayPreviewIconUrl).exists())
        assert(File(dummyOverlay3.overlayUrl).exists())
        assert(File(dummyOverlay4.overlayPreviewIconUrl).exists())
        assert(File(dummyOverlay4.overlayUrl).exists())

        overlayDao._insert(dummyOverlay3)
        overlayDao._insert(dummyOverlay4)
        runBlocking {

            overlayDao.deleteAllOverlay()

            assert(File(dummyOverlay3.overlayPreviewIconUrl).exists().not())
            assert(File(dummyOverlay3.overlayUrl).exists().not())
            assert(File(dummyOverlay4.overlayPreviewIconUrl).exists().not())
            assert(File(dummyOverlay4.overlayUrl).exists().not())

            assert(overlayDao.getAllOverlay().first().isEmpty())
        }

    }


    @After
    fun closeDb() {
        appDatabase.close()
    }
}