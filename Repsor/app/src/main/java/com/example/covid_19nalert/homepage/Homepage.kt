package com.example.covid_19nalert.homepage


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.covid_19nalert.NcdcTwitterActivity
import com.example.covid_19nalert.R
import com.example.covid_19nalert.databinding.ExperimentBinding
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Application homepage
 */
class Homepage : Fragment() {
    private val TOPIC = "News"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding = DataBindingUtil.inflate<ExperimentBinding>(inflater, R.layout.experiment, container, false)
        binding.newsId.setOnClickListener{
            binding.newsId.setCardBackgroundColor(Color.GRAY)
            binding.trackId.animate()
            findNavController().navigate(HomepageDirections.actionHomepageToCaseSummary())
        }

        binding.trackId.setOnClickListener {
            binding.trackId.setCardBackgroundColor(Color.GRAY)
            binding.trackId.animate()
            findNavController().navigate(HomepageDirections.actionHomepageToCoronaStatistic())
            //binding.trackId.setBackgroundColor(Color.rgb(0,115,79))
        }

        binding.learnId.setOnClickListener {
            binding.learnId.setCardBackgroundColor(Color.GRAY)
            binding.trackId.animate()
            findNavController().navigate(HomepageDirections.actionHomepageToLearnMore())
        }

        binding.ncdcTwitterId.setOnClickListener {
            val intent = Intent(context, NcdcTwitterActivity::class.java )
            intent.putExtra("ncdcTwitterHandle", "https://twitter.com/NCDCgov")
            binding.ncdcTwitterId.setCardBackgroundColor(Color.GRAY)
            binding.trackId.animate()
            startActivity(intent)
        }

        //creating notification channel
        createNotificationChannel(getString(R.string.repsor_notification_channel_id), getString(R.string.repsor_notification_channel))

        subscribeTopic()
        return binding.root
    }


    private fun createNotificationChannel(channelID : String, channelName : String){
        /**create notification channel only if the current SDK version is lower than API 26**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)


            notificationChannel.shouldVibrate()
            notificationChannel.setShowBadge(false)
            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.description = "Repsor latest COVID-19 news notification"

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

   /**Subscribe to news topic**/
    private fun subscribeTopic() {
        // [START subscribe_topic]
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }

    companion object {
        fun newInstance() = Homepage()
    }
}
