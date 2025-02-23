package app.benchmate.di

import android.content.Context
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.player.PlayerRepository
import app.benchmate.repositories.player.RealPlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlayerRepository(
        @ApplicationContext context: Context
    ): PlayerRepository {
        return RealPlayerRepository(DatabaseDriverFactory(context.applicationContext))
    }
}