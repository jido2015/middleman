package com.project.middleman.core.injection

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.middleman.core.source.data.DispatchProvider
import com.project.middleman.core.source.data.authentication.AuthRepositoryImpl
import com.project.middleman.core.source.data.profile.ProfileRepositoryImpl
import com.project.middleman.core.source.domain.repository.AuthRepository
import com.project.middleman.core.source.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        auth: FirebaseAuth,
        db: FirebaseFirestore,

        ): AuthRepository = AuthRepositoryImpl(
        activityContext = context,
        auth = auth,
        db = db)

    @Provides
    fun provideProfileRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): ProfileRepository = ProfileRepositoryImpl(
        auth = auth,
        db = db
    )


    @Provides
    fun provideDispatchers(): DispatchProvider = object : DispatchProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}