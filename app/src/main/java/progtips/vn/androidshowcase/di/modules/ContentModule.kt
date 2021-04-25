package progtips.vn.androidshowcase.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_DATASTORE_NAME = "APP_DATASTORE_NAME"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
class ContentModule {
    @Provides
    @Singleton
    fun providesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }
}