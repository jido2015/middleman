package com.project.middleman.core.injection

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.middleman.core.source.data.DispatchProvider
import com.project.middleman.core.source.data.authentication.AuthRepositoryImpl
import com.project.middleman.core.source.data.challenge.ChallengeRepositoryImpl
import com.project.middleman.core.source.data.model.User
import com.project.middleman.core.source.data.profile.ProfileRepositoryImpl
import com.project.middleman.core.source.domain.authentication.repository.AuthRepository
import com.project.middleman.core.source.domain.authentication.repository.ProfileRepository
import com.project.middleman.core.source.domain.challenge.repository.ChallengeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore


    @Provides
    fun providesCurrentUser(): User {
        val currentUser = Firebase.auth.currentUser
        return if (currentUser != null) {
            User(
                uid = currentUser.uid,
                displayName = currentUser.displayName.orEmpty(),
                email = currentUser.email.orEmpty(),
                photoUrl = currentUser.photoUrl.toString(),
                createdAt = FieldValue.serverTimestamp()
            )
        } else {
            User() // Returns an empty/default user
        }
    }


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
    fun provideChallengeRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): ChallengeRepository = ChallengeRepositoryImpl(
        db = db)

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