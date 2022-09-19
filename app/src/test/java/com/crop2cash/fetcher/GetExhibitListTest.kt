package com.crop2cash.fetcher

import com.crop2cash.fetcher.common.Resource
import com.crop2cash.fetcher.data.repository.FakeRestExhibitLoader
import com.crop2cash.fetcher.domain.model.Exhibit
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetExhibitListTest {
    private lateinit var fakeRestExhibitLoader: FakeRestExhibitLoader

    @Before
    fun setup() {
        fakeRestExhibitLoader = FakeRestExhibitLoader()

        val exhibitsToInsert = mutableListOf<Exhibit>()

        ('a'..'z').forEachIndexed { index, c ->
            exhibitsToInsert.add(
                Exhibit(
                    id = index,
                    title = c.toString(),
                    images = listOf(index.toString())
                )
            )
        }
        exhibitsToInsert.shuffle()
        runBlocking {
            fakeRestExhibitLoader.insertExhibits(exhibitsToInsert)
        }
    }

    @Test
    fun `ExhibitList is not empty`() = runBlocking {
        val exhibits = fakeRestExhibitLoader.getExhibitList(true, "").first()
        val list = mutableListOf<List<Exhibit>>()
        when(exhibits) {
                is Resource.Success -> {
                    list.add(exhibits.data!!)
                }
                else -> {}
            }

        assertThat(list.toList()).isNotEmpty()
    }
}