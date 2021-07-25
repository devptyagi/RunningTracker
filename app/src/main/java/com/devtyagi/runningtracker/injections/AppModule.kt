package com.devtyagi.runningtracker.injections

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.devtyagi.runningtracker.database.RunningDatabase
import com.devtyagi.runningtracker.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.devtyagi.runningtracker.other.Constants.KEY_NAME
import com.devtyagi.runningtracker.other.Constants.KEY_WEIGHT
import com.devtyagi.runningtracker.other.Constants.RUNNING_DATABASE_NAME
import com.devtyagi.runningtracker.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(database: RunningDatabase) = database.getRunDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPrefs: SharedPreferences) = sharedPrefs.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPrefs: SharedPreferences) = sharedPrefs.getFloat(KEY_WEIGHT, 0f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPrefs: SharedPreferences) =
        sharedPrefs.getBoolean(KEY_FIRST_TIME_TOGGLE, true)

}