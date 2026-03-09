package dk.chen.garbagev1.data.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.chen.garbagev1.data.ItemRepositoryImpl
import dk.chen.garbagev1.data.BinRepositoryImpl
import dk.chen.garbagev1.data.UserPreferencesRepositoryImpl
import dk.chen.garbagev1.domain.ItemRepository
import dk.chen.garbagev1.domain.BinRepository
import dk.chen.garbagev1.domain.UserPreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository

    @Singleton
    @Binds
    abstract fun bindBinRepository(impl: BinRepositoryImpl): BinRepository

    @Singleton
    @Binds
    abstract fun bindUserPreferencesRepository(impl: UserPreferencesRepositoryImpl): UserPreferencesRepository
}
