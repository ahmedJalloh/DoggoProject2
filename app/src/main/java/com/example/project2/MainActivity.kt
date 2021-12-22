package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.project2.database.DogImageEntity
import com.example.project2.viewmodels.MainViewModel
import com.example.project2.viewmodels.MainViewModelFactory
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as DogApplication).database.dogImageDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val changeDogButton: Button = findViewById(R.id.fetchNewDogButton)
        val prevDogButton :Button = findViewById(R.id.fetchPreviousDogButton)

        viewModel.currentlyDisplayedImage.observe(this,
            {
                val mainImage : ImageView = findViewById(R.id.dogImage)
                Picasso.with(this).load(it.imageUrl).into(mainImage)
            })

        changeDogButton.setOnClickListener {
            val currentImgUrl = viewModel.currentlyDisplayedImage.value?.imageUrl
            val newDogImage = currentImgUrl?.let { it1 -> DogImageEntity(imageUrl = it1) }

            viewModel.getNewDog()
            if (newDogImage != null) {
                viewModel.addDog(newDogImage)
            }
            viewModel.deleteMostRecentDog()
        }

        prevDogButton.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }


}
