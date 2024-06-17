package com.protectly.alkewallet

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.protectly.alkewallet.model.LoginRequest
import com.protectly.alkewallet.model.LoginResponse
import com.protectly.alkewallet.model.UserResponse
import com.protectly.alkewallet.model.network.ApiService
import com.protectly.alkewallet.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var loginObserver: Observer<Boolean>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var userDataObserver: Observer<UserResponse?>

    @Captor
    private lateinit var loginCaptor: ArgumentCaptor<Boolean>

    @Captor
    private lateinit var loadingCaptor: ArgumentCaptor<Boolean>

    @Captor
    private lateinit var userDataCaptor: ArgumentCaptor<UserResponse?>

    private lateinit var viewModel: LoginViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val application = mock(Application::class.java)
        `when`(application.getSharedPreferences(anyString(), anyInt())).thenReturn(mock(Context::class.java).getSharedPreferences("test", Context.MODE_PRIVATE))
        viewModel = LoginViewModel(application, )
        viewModel.loginResultLiveData.observeForever(loginObserver)
        viewModel.loadingLiveData.observeForever(loadingObserver)
        viewModel.userDataLiveData.observeForever(userDataObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun login_success() = runBlockingTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val loginResponse = LoginResponse("test_token", null, 200)

        `when`(apiService.login(LoginRequest(email, password))).thenReturn(loginResponse)

        // Act
        viewModel.login(email, password)

        // Assert
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        Assert.assertTrue(loadingCaptor.allValues[0])
        Assert.assertFalse(loadingCaptor.allValues[1])

        verify(loginObserver, times(1)).onChanged(loginCaptor.capture())
        Assert.assertTrue(loginCaptor.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun login_failure() = runBlockingTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val loginResponse = LoginResponse(null, "Invalid credentials", 401)

        `when`(apiService.login(LoginRequest(email, password))).thenReturn(loginResponse)

        // Act
        viewModel.login(email, password)

        // Assert
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        Assert.assertTrue(loadingCaptor.allValues[0])
        Assert.assertFalse(loadingCaptor.allValues[1])

        verify(loginObserver, times(1)).onChanged(loginCaptor.capture())
        Assert.assertFalse(loginCaptor.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getUserData_success() = runBlockingTest {
        // Arrange
        val token = "test_token"
        val userResponse = UserResponse(1, "John", "Doe", "john.doe@example.com", "password", 2, 100)

        `when`(apiService.getUserData("Bearer $token")).thenReturn(retrofit2.Response.success(userResponse))
        viewModel.accessTokenVm = token

        // Act
        viewModel.getUserData()

        // Assert
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        Assert.assertTrue(loadingCaptor.allValues[0])
        Assert.assertFalse(loadingCaptor.allValues[1])

        verify(userDataObserver, times(1)).onChanged(userDataCaptor.capture())
        Assert.assertEquals(userResponse, userDataCaptor.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getUserData_failure() = runBlockingTest {
        // Arrange
        val token = "test_token"

        `when`(apiService.getUserData("Bearer $token")).thenReturn(retrofit2.Response.error(401, okhttp3.ResponseBody.create(null, "Unauthorized")))
        viewModel.accessTokenVm = token

        // Act
        viewModel.getUserData()

        // Assert
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        Assert.assertTrue(loadingCaptor.allValues[0])
        Assert.assertFalse(loadingCaptor.allValues[1])

        verify(userDataObserver, times(1)).onChanged(userDataCaptor.capture())
        Assert.assertNull(userDataCaptor.value)
    }
}
