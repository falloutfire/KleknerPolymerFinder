package com.klekner.polymerfinder.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.klekner.polymerfinder.data.Polymer
import com.klekner.polymerfinder.data.PolymerBlend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Database(entities = [Polymer::class, PolymerBlend::class], version = 1)
abstract class PolymerDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "KEEP_DATABASE"
        @Volatile
        private var INSTANCE: PolymerDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PolymerDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PolymerDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(
                    AppDatabaseCallback(
                        scope
                    )
                )
                    .build()
                INSTANCE = instance
                //populateDatabase(instance.userDao(), instance.articleDao())
                return instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.polymersDao)
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        //populateDatabase(database.userDao(), database.articleDao())
                    }
                }
            }
        }

        private fun populateDatabase(polymersDao: PolymerDao) {
            runBlocking(Dispatchers.IO) {
                val polymer1 = Polymer("poly(acrylonitrile-co-butadiene-co-styrene)", "ABS", "9003-56-9", "the most frequently used are emulsion, mass, and suspension polymerizations; styrene and acrylonitrile are being grafted onto rubber by chemical grafting, chemical grafting blending, or physical mixing; chemical grafting blending is the most frequently used method and speci cally emulsion grafting-bulk SAN blending is a method of choice",
                    1.03, 1.09, 220, 260, 80, "Transparent")
                val polymer2 = Polymer("polycarbonate", "PC", "25037-45-0; 25766-59-0", "Bisphenol A is treated with NaOH, which is then reacted with phosgene. It can also be manufactured by transesteri cation of bisphenol A with diphenyl carbonate.",
                    1.19, 1.22, 255, 267, 130, "Transparent")
                val polymer3 = Polymer("polyamide-6", "PA-6", "25038-54-4", "caprolactam is melted and polymerized in the presence of catalyst and dulling agent.",
                    1.06, 1.16, 220, 260, 130, "Transparent")
                val polymer4 = Polymer("poly(lactic acid)", "PLA", "51063-13-9, 26680-10-4, 34346-01-5", "lactic acid is heated at 150oC to obtain oligomeric PLA (polymerization degree: 1-8). Oligomers are heated at 180oC under vacuum for 5 hours to give PLA having molecular weight of 100,00.",
                    1.21, 1.29, 164, 178, 0, "Transparent")
                val polymer5 = Polymer("polyaniline", "PANI", "25233-30-1", "p-toluenesulfonic acid protonated aniline is used to make anilinium complexes; slow addition of ammonium peroxydisulfate caused formation of polyaniline in the micelles and grew to needle-like aggregates potentially useful as conductive llers.",
                    1.36, 1.4, 385, 385, 0, "Transparent")
                val polymer6 = Polymer("alkyd resin", "AK", "63148-69-6; 68333-62-0", "the mixture of oil, glycerol, and catalyst is heated to a required temperature and phthalic anhydride is added to accomplish esterication",
                    1.1, 1.25, 0, 0, 0, "Transparent")
                val polymer7 = Polymer("poly(acrylonitrile-co-styrene-co-acrylate)", "ASA", "9003-54-7; 26299-47-8; 26716-29-0", "grafting rubber which is dispersed with a styrene acrylonitrile (SAN) phase ",
                    1.06, 1.1, 180, 200, 0, "Transparent")

                polymersDao.insertPolymer(polymer1)
                polymersDao.insertPolymer(polymer2)
                polymersDao.insertPolymer(polymer3)
                polymersDao.insertPolymer(polymer4)
                polymersDao.insertPolymer(polymer5)
                polymersDao.insertPolymer(polymer6)
                polymersDao.insertPolymer(polymer7)

                val blends = arrayListOf<PolymerBlend>(
                    PolymerBlend(1,2),
                    PolymerBlend(1,3),
                    PolymerBlend(1,4),
                    PolymerBlend(1,5),
                    PolymerBlend(2,3),
                    PolymerBlend(2,4),
                    PolymerBlend(2,1),
                    PolymerBlend(3,1),
                    PolymerBlend(4,1),
                    PolymerBlend(5,1))

                polymersDao.insertBlends(*blends.toTypedArray())
            }
        }

    }

    abstract val polymersDao: PolymerDao
}