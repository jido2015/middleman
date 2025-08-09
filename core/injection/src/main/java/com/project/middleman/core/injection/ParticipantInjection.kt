package com.project.middleman.core.injection;

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.repository.challenge.ChallengeRepositoryImpl
import com.project.middleman.core.source.data.repository.challenge.ParticipantRepositoryImpl
import com.project.middleman.core.source.domain.challenge.repository.ChallengeRepository
import com.project.middleman.core.source.domain.challenge.repository.ParticipantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ParticipantInjection {

    @Provides
    fun provideParticipantRepository(
        db: FirebaseFirestore
    ): ParticipantRepository = ParticipantRepositoryImpl(
        db = db)
}
