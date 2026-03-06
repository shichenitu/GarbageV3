package dk.chen.garbagev1.domain

import kotlinx.coroutines.flow.Flow

interface BinRepository {
    fun getBins(): Flow<List<Bin>>
}