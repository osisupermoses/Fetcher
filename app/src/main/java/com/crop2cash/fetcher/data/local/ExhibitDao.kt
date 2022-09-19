package com.crop2cash.fetcher.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crop2cash.fetcher.data.local.ExhibitListingEntity

@Dao
interface ExhibitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExhibitListings(
        exhibits: List<ExhibitListingEntity>
    )

    @Query("DELETE FROM exhibitlistingentity")
    suspend fun clearExhibitListings()

    @Query(
        """
            SELECT *
            FROM exhibitlistingentity
            WHERE LOWER(title) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == title
        """
    )
    suspend fun searchExhibitListing(query: String): List<ExhibitListingEntity>
}