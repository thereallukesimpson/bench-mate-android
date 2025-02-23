package app.benchmate.ui.features.bench

import app.benchmate.R
import app.benchmate.common.usecase.PlayerUseCase
import app.benchmate.utils.TestViewModelScopeRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever

class BenchViewModelTest {

    @get:Rule
    val dispatcherRule = TestViewModelScopeRule(testDispatcher = StandardTestDispatcher())

    private val mockPlayerUseCase: PlayerUseCase = mock()
    private lateinit var viewModel: BenchViewModel

    @Before
    fun setUp() = runTest {
        whenever(mockPlayerUseCase.getAllPlayers()).thenReturn(
            BenchTestData.getPlayers()
        )
    }

    @After
    fun tearDown() {
        reset(
            mockPlayerUseCase
        )
    }

    @Test
    fun givenInitialState_whenExistingPlayers_thenDisplayPlayersEmitted() = runTest {
        viewModel = BenchViewModel(
            playerUseCase = mockPlayerUseCase
        )

        viewModel.team.test {
            val initialState = awaitItem() as BenchViewModel.ViewState.Empty
            assertThat(initialState).isInstanceOf(BenchViewModel.ViewState.Empty::class.java)
            assertThat(initialState.message).isEqualTo(R.string.empty_state_message)

            val teamState = awaitItem() as BenchViewModel.ViewState.Team
            assertThat(teamState).isInstanceOf(BenchViewModel.ViewState.Team::class.java)
            assertThat(teamState.name).isEqualTo("My Team")
            assertThat(teamState.list.size).isEqualTo(2)

            expectNoEvents()
        }
    }
}