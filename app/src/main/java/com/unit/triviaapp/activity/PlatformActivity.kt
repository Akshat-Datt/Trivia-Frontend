package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.unit.triviaapp.constants.ConstCardValues
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.databinding.ActivityPlatformBinding
import com.unit.triviaapp.models.Platforms
import com.unit.triviaapp.network.QuizApiManager

class PlatformActivity: AppCompatActivity(){
    private lateinit var binding: ActivityPlatformBinding
    private lateinit var platforms: ArrayList<Platforms>
    private lateinit var platformsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlatformBinding.inflate(layoutInflater)
        setContentView(binding.root)
        platformsContainer = binding.llContainer

        platforms = intent.getParcelableArrayListExtra(ConstKeys.PLATFORMS_LIST, Platforms::class.java)?: return

        for(platform in platforms){
            PopulatePlatforms(platform, platformsContainer)
        }
    }

    private fun PopulatePlatforms(platform: Platforms, platformsContainer: LinearLayout){
        val platformCard = MaterialCardView(this)
        val platformText = TextView(this)

        platformText.text = platform.platform_name

        platformText.textSize = ConstCardValues.CARD_TEXT_SIZE
        platformCard.addView(platformText)
        platformCard.setContentPadding(
            24,
            20,
            24,
            20
        )
        platformCard.radius = ConstCardValues.CARD_RADIUS
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        platformCard.setOnClickListener {
            QuizApiManager.getEndlessQuestionsList(
                platform.id,
                null,
                null,
                onSuccess = {endlessQuizResponse ->
                    Log.d("Trivia", "Endless quiz response $endlessQuizResponse")
                },
                onError = {error ->
                    Log.d("Trivia", "Error while clicking on ${platform.platform_name}")
                }
                )
        }
        params.bottomMargin = ConstCardValues.CARD_BOTTOM_MARGIN
        platformCard.layoutParams = params
        platformsContainer.addView(platformCard)
    }
}