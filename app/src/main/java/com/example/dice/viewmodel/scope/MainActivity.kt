package com.example.dice.viewmodel.scope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.dice.viewmodel.scope.databinding.ActivityMainBinding

val diceImages: List<Int> by lazy {
    listOf(
        R.drawable.dice_face_one,
        R.drawable.dice_face_two,
        R.drawable.dice_face_three,
        R.drawable.dice_face_four,
        R.drawable.dice_face_five,
        R.drawable.dice_face_six,
    )
}

class DicesViewModel : ViewModel() {
    private val _dicesThrow = MutableLiveData<Pair<Int, Int>>()
    val dicesThrow: LiveData<Pair<Int, Int>> = _dicesThrow

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val images = listOf(
            binding.firstDiceImage,
            binding.secondDiceImage,
            binding.thirdDiceImage,
        )
        val dicesViewModel: DicesViewModel = ViewModelProvider(this).get(DicesViewModel::class.java)


        setContentView(binding.root)
    }
}