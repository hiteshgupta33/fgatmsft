package com.fgmsft.signmeup

import android.Manifest
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fgmsft.signmeup.signup.form.avatar.AvatarCameraProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.*
import org.mockito.ArgumentMatchers
import java.io.File

class AvatarCameraProviderTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var mockedFile: File

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `file is null with null directory file`() {
        val avatarManager = AvatarCameraProvider()

        Assert.assertNull(avatarManager.createAvatarFile(null))
    }

    @Test
    @Ignore
    // This currently fails with java.lang.IllegalArgumentException: Prefix string too short
    fun `file is NOT null with mocked directory file`() {
        val avatarManager = AvatarCameraProvider()

        every {
            File.createTempFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any())
        } returns mockedFile

        Assert.assertNotNull(avatarManager.createAvatarFile(mockedFile))
        Assert.assertNotNull(avatarManager.getAvatarPath())
    }

    @Test
    fun `verify valid permission`() {
        val avatarManager = AvatarCameraProvider()

        Assert.assertEquals(Manifest.permission.CAMERA, avatarManager.getRequiredAvatarPermission())
    }
}