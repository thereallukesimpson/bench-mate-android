package app.benchmate.ui.features.bench

import app.benchmate.common.usecase.PlayerUseCase
import org.junit.Before
import org.mockito.kotlin.mock

class BenchViewModelTest {

    private val mockPlayerUseCase: PlayerUseCase = mock()
    private lateinit var viewModel: BenchViewModel

    @Before
    fun setUp() {
        viewModel = BenchViewModel(
            playerUseCase = mockPlayerUseCase
        )
    }



}