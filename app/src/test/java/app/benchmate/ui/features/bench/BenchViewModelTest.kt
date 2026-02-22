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
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
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

            verify(mockPlayerUseCase, times(1)).getAllPlayers()
        }
    }

    @Test
    fun givenTeamState_whenClearBench_thenPlayersEmitted() = runTest {
        viewModel = BenchViewModel(
            playerUseCase = mockPlayerUseCase
        )
        viewModel.team.test {
            skipItems(2) // Skip empty and initial team state
            verify(mockPlayerUseCase, times(1)).getAllPlayers()
            viewModel.clearBench()

            awaitItem() // Event triggered after clearBench but just reflect mocked values

            verify(mockPlayerUseCase, times(1)).clearBenchCountAndPlayerStatus()
            verify(mockPlayerUseCase, times(2)).getAllPlayers()
        }
    }

    @Test
    fun givenZeroMs_whenFormatBenchTime_thenReturnsZeroMinutesZeroSeconds() {
        assertThat(BenchViewModel.formatBenchTime(0L)).isEqualTo("0m 00s")
    }

    @Test
    fun givenSubMinuteMs_whenFormatBenchTime_thenReturnsZeroMinutesWithSeconds() {
        assertThat(BenchViewModel.formatBenchTime(30_000L)).isEqualTo("0m 30s")
    }

    @Test
    fun givenExactMinuteMs_whenFormatBenchTime_thenReturnsOneMinuteZeroSeconds() {
        assertThat(BenchViewModel.formatBenchTime(60_000L)).isEqualTo("1m 00s")
    }

    @Test
    fun givenMinutesAndSecondsMs_whenFormatBenchTime_thenReturnsFormattedTime() {
        assertThat(BenchViewModel.formatBenchTime(90_000L)).isEqualTo("1m 30s")
    }

    @Test
    fun givenSingleDigitSeconds_whenFormatBenchTime_thenSecondsPaddedToTwoDigits() {
        assertThat(BenchViewModel.formatBenchTime(65_000L)).isEqualTo("1m 05s")
    }

    @Test
    fun givenAddPlayer_whenPlayerAdded_thenPlayerEmitted() = runTest {
        viewModel = BenchViewModel(
            playerUseCase = mockPlayerUseCase
        )

        viewModel.team.test {
            skipItems(2) // Skip empty and initial team state
            viewModel.addPlayerToTeam(
                name = "Test",
                number = 1
            )

            awaitItem() // Event triggered after addPlayerToTeam but just reflects mocked values

            verify(mockPlayerUseCase, times(2)).getAllPlayers()
            verify(mockPlayerUseCase, times(1)).addPlayer(any(), any(), any(), any())
        }
    }
}