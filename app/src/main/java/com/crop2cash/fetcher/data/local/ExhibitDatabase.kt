package com.crop2cash.fetcher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ExhibitListingEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExhibitDatabase: RoomDatabase() {
    abstract val dao: ExhibitDao
}