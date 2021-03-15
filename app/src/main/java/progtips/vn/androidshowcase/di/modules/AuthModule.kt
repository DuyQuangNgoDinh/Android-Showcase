package progtips.vn.androidshowcase.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import progtips.vn.asia.authfirebase.FirebaseAuthManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun providesFirebaseAuthManager(): FirebaseAuthManager = FirebaseAuthManager()

}