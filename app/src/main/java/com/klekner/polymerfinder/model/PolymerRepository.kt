package com.klekner.polymerfinder.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.klekner.polymerfinder.data.Polymer
import com.klekner.polymerfinder.data.PolymerWithBlends
import com.klekner.polymerfinder.db.PolymerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PolymerRepository(
    private val dao: PolymerDao/*,
    private val remoteSource: KeepsRemoteDataSource*/
) {

    /*val polymers = resultLiveData(
        databaseQuery = { dao.getPolymers() }),
        networkCall = { remoteSource.fetchData() },
        saveCallResult = { dao.insertKeeps(*it.toTypedArray()) })*/

    val polymers = dao.getPolymers()

    fun addPolymer(item: Polymer, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            /*val polymer1 = Polymer("poly(acrylonitrile-co-butadiene-co-styrene)", "ABS", "9003-56-9", "the most frequently used are emulsion, mass, and suspension polymerizations; styrene and acrylonitrile are being grafted onto rubber by chemical grafting, chemical grafting blending, or physical mixing; chemical grafting blending is the most frequently used method and speci cally emulsion grafting-bulk SAN blending is a method of choice",
                1.03, 1.09, 220, 260, 80, "Transparent")*/
            dao.insertPolymer(item)
        }
    }

    fun getPolymer(id: Long): LiveData<Polymer> {
        return liveData {
            val data = dao.getPolymer(id)
            emitSource(data)
        }
    }

    fun getBlends(id: Long): LiveData<List<Polymer>?> {
        return liveData {
            val data = dao.getPolymerWithBlends(id).map { it?.blends }
            emitSource(data)
        }
    }
}
