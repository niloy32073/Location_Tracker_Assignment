package com.tracky.tracker.di

import com.tracky.tracker.data.api.AuthApiService
import com.tracky.tracker.data.repositories.AuthRepository
import com.tracky.tracker.data.repositories.AuthRepositoryImpl
import com.tracky.tracker.data.service.LocationRepository
import com.tracky.tracker.domain.usecase.LoginUseCase
import com.tracky.tracker.domain.usecase.SendLocationUseCase
import com.tracky.tracker.presentation.login.LoginViewModel
import com.tracky.tracker.presentation.main.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
    }

    single { AuthApiService(get(), "https://portal.truck-y.com/api") }
    single<AuthRepository> { AuthRepositoryImpl(get(), androidContext()) }
    single { LoginUseCase(get()) }
    single { SendLocationUseCase(get()) }
    single { LocationRepository(get(), get(),"https://portal.truck-y.com/api") }

    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
}

