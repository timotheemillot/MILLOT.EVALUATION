package org.mathieu.data.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.mathieu.data.local.CharacterLocal
import org.mathieu.data.local.LocationLocal
import org.mathieu.data.local.RealmDatabase
import org.mathieu.data.remote.CharacterApi
import org.mathieu.data.remote.LocationApi
import org.mathieu.data.repositories.CharacterRepositoryImpl
import org.mathieu.data.repositories.LocationRepositoryImpl
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.domain.repositories.LocationRepository

//https://rickandmortyapi.com/documentation/#rest
private const val RMAPI_URL = "https://rickandmortyapi.com/api/"

val dataModule = module {

    single<HttpClient> {
        org.mathieu.data.remote.createHttpClient(
            baseUrl = RMAPI_URL
        )
    }

    single<RealmDatabase> { org.mathieu.data.local.RMDatabase() }

    single { CharacterLocal(get()) }

    single { CharacterApi(get()) }

    single<CharacterRepository> {
        CharacterRepositoryImpl(
            get(),
            get(),
            get(),
            get()
        )
    }

    single { LocationLocal(get()) }

    single { LocationApi(get()) }

    single<LocationRepository> { LocationRepositoryImpl(get(), get(), get(), get()) }

}