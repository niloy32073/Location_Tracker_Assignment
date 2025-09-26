package com.tracky.tracker

import android.app.Application
import com.tracky.tracker.data.api.AuthApiService
import com.tracky.tracker.data.repositories.AuthRepository
import com.tracky.tracker.data.repositories.AuthRepositoryImpl
import com.tracky.tracker.di.appModule
import com.tracky.tracker.presentation.login.LoginViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
