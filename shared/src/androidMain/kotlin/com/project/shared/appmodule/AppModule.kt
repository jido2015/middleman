package com.project.shared.appmodule

import com.project.shared.provider.AndroidFirebaseProvider
import com.project.shared.provider.FirebaseProvider
import org.koin.dsl.module

// Provide via Koin
val appModule = module {
    single<FirebaseProvider> { AndroidFirebaseProvider(get())} }