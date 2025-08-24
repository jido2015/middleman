package com.project.middleman.core.injection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.repository.challenge.ChallengeRepositoryImpl
import com.project.middleman.core.source.data.repository.challenge.ConcludeChallengeRepositoryImpl
import com.project.middleman.core.source.data.repository.challenge.FetchChallengesRepositoryImpl
import com.project.middleman.core.source.domain.challenge.repository.ChallengeRepository
import com.project.middleman.core.source.domain.challenge.repository.ConcludeChallengeRepository
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ChallengeInjection {

    @Provides
    fun provideChallengeRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): ChallengeRepository = ChallengeRepositoryImpl(
        db = db, firebaseAuth = auth)

    @Provides
    fun provideConcludeChallengesRepository(
        db: FirebaseFirestore
    ): ConcludeChallengeRepository = ConcludeChallengeRepositoryImpl(
        db = db)

    @Provides
    fun provideFetchChallengesRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): FetchChallengesRepository = FetchChallengesRepositoryImpl(
        db = db)
}
