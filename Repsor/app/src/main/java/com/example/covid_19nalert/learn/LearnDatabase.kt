package com.example.covid_19nalert.learn

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**Room's entity for the database row**/
@Entity(tableName = "learn_resource_table") @Parcelize
data class LearnEntity(
    val urlToLearningResource : String,
    @PrimaryKey val learningResourceTitle : String,
    val learningResourceShortDescription : String,
    val id : Int
    ) : Parcelable

/**Data access objects for database queries**/
@Dao
interface LearnDao{
    @Insert
    fun insertListOfLearningResource(inputList : List<LearnEntity>)

    @Query("SELECT * FROM learn_resource_table")
    fun retrieveAllLearningResource() : LiveData<List<LearnEntity>>
}

/**Room's database class for the learning resources**/
@Database(entities = [LearnEntity::class], version = 2, exportSchema = false)
abstract class LearnDatabase : RoomDatabase(){
    abstract val learnDao : LearnDao
    /**Loading all learning resource the first time the database is created**/
    class LearnDatabaseCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch (Dispatchers.IO){
                    val learnDbDao = it.learnDao

                   val travelAdvice = LearnEntity(
                       "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/travel-advice",
                       "Travel Advice",
                       "WHO's advice on International traffic",
                       2
                   )

                    val mythBusters = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public/myth-busters",
                        "Myth Busters",
                        "WHO's stand on COVID-19 myths", 3
                    )

                    val responseFund = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/donate",
                        "COVID-19 Response Fund",
                        "Help WHO fight COVID-19", 4
                    )

                    val usingMasks = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public/when-and-how-to-use-masks",
                        "Masks",
                        "When and How to use masks", 5
                    )

                    val parenting = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public/healthy-parenting",
                        "Healthy Parenting",
                        "Parenting in time of COVID-19", 6
                    )

                    val technicalGuidance = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/technical-guidance",
                        "Technical Guidance",
                        "All technical guidance by topic", 7
                    )
                    val situationReport = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/situation-reports",
                        "WHO's Situation Report",
                        "Daily reports from WHO", 8
                    )

                    val ncdcSituationReport = LearnEntity(
                        "https://ncdc.gov.ng/diseases/sitreps/?cat=14&name=An%20update%20of%20COVID-19%20outbreak%20in%20Nigeria",
                        "Nigeria Center for Disease Control",
                        "Daily Situation Report", 9
                    )

                    val ncdcEmergencyContacts = LearnEntity(
                        "https://covid19.ncdc.gov.ng/statecontacts.html",
                        "NCDC Emergency Contacts" ,
                        "All 36 States Emergency Contacts",
                        1

                    )

                    val preventTheSpread = LearnEntity(
                        "https://www.youtube.com/watch?v=8c_UJwLq8PI",
                        "Prevent the spread of the Virus",
                        "Seven steps to prevent the spread", 10
                    )

                    val symptoms = LearnEntity(
                        "https://www.cdc.gov/coronavirus/2019-ncov/symptoms-testing/symptoms.html",
                        "Symptoms of Corona virus",
                        "Watch for these symptoms", 11
                    )

                    val whatToDoIfYouAreSick = LearnEntity(
                        "https://www.cdc.gov/coronavirus/2019-ncov/if-you-are-sick/steps-when-sick.html",
                        "If You Are Sick",
                        "What to do if you are sick", 12
                    )

                    val caringForSomeOneWhoIsSick = LearnEntity(
                        "https://www.cdc.gov/coronavirus/2019-ncov/if-you-are-sick/care-for-someone.html",
                        "Caring for a Sick Person",
                        "How to care for a sick person", 13
                    )

                    val peopleWhoNeedExtraPrecaution = LearnEntity(
                        "https://www.cdc.gov/coronavirus/2019-ncov/need-extra-precautions/index.html",
                        "People who are at higher risk",
                        "People who needs extra precaution", 14
                    )

                    val frequentlyAskedQuestions = LearnEntity(
                        "https://www.cdc.gov/coronavirus/2019-ncov/faq.html",
                        "FAQ",
                        "CDC Frequently Asked Questions", 15
                    )

                    val testingForCovid = LearnEntity(
                        "https://www.cdc.gov/coronavirus/2019-ncov/symptoms-testing/testing.html",
                        "Testing for COVID-19",
                        "When should you get tested?", 16
                    )

                    val ncdcFrequentlyAskedQuestions = LearnEntity(
                        "https://ncdc.gov.ng/news/item/241/?t=frequently-asked-questions-on-coronavirus---15%2F03%2F2020",
                        "NCDC FAQ",
                        "NCDC Frequently Asked Questions ", 17
                    )

                    val reasearchAndDevelopment = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/global-research-on-novel-coronavirus-2019-ncov",
                        "Research and Development",
                        "Global research on COVID-19", 18
                    )

                    val yourQuestionsAnswered = LearnEntity(
                        "https://www.who.int/news-room/q-a-detail/q-a-coronaviruses",
                        "Your Questions Answered",
                        "WHO'S Q&A on coronaviruses", 19
                    )
                    val ncdcTwitter = LearnEntity(
                        "https://twitter.com/NCDCgov",
                        "NCDC Direct Updates",
                        "All latest news from NCDC",
                        20
                    )
                    val whoCovid19Training = LearnEntity(
                        "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/training/online-training",
                        "Coronavirus Disease Training",
                        "WHO's online training on COVID-19",
                        21
                    )
                    val publicEducationSocialDistancing = LearnEntity(
                        "https://www.canada.ca/en/public-health/services/video/reduce-spread-covid-19-social-distancing.html",
                        "Social Distancing",
                        "Impacts of social distancing",
                        22
                    )

                    val caringForAChildWithCovid19 = LearnEntity(
                        "https://www.canada.ca/en/public-health/services/publications/diseases-conditions/how-to-care-for-child-with-covid-19-at-home-advice-for-caregivers.html",
                        "How to care for a child",
                        "Caring for a child with COVID-19 at home",
                        23
                    )

                    val mentalHealth = LearnEntity(
                        "https://www.canada.ca/en/public-health/services/publications/diseases-conditions/taking-care-mental-health.html",
                        "Your Mental Health",
                        "Taking care of your mental health",
                        24
                    )
                   learnDbDao.insertListOfLearningResource(listOf(
                       ncdcTwitter,
                       ncdcEmergencyContacts,
                       preventTheSpread,
                       symptoms,
                       testingForCovid,
                       whatToDoIfYouAreSick,
                       caringForSomeOneWhoIsSick,
                       caringForAChildWithCovid19,
                       peopleWhoNeedExtraPrecaution,
                       publicEducationSocialDistancing,
                       yourQuestionsAnswered,
                       mythBusters,
                       mentalHealth,
                       whoCovid19Training,
                       frequentlyAskedQuestions,
                       reasearchAndDevelopment,
                       ncdcFrequentlyAskedQuestions,
                       parenting,
                       usingMasks,
                       ncdcSituationReport,
                       situationReport,
                       technicalGuidance,
                       responseFund,
                       travelAdvice
                   ))
                }
            }
        }
    }




    companion object {
        @Volatile
        private var INSTANCE: LearnDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope) : LearnDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        LearnDatabase::class.java,
                        "learn_resources").fallbackToDestructiveMigration().addCallback(LearnDatabaseCallBack(scope)).build()
                    /**you can add .fallBackToDestrutiveMigration**/
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}