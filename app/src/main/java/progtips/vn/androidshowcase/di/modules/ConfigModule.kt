package progtips.vn.androidshowcase.di.modules

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import progtips.vn.androidshowcase.BuildConfig
import progtips.vn.asia.firebaseconfig.AppConfig

@Module
@InstallIn(ActivityComponent::class)
class ConfigModule {
    @Provides
    fun providesAppConfig(@ActivityContext context: Context): AppConfig {
        return AppConfig(context as Activity, BuildConfig.CONFIG_FETCH_INTERVAL)
    }
}