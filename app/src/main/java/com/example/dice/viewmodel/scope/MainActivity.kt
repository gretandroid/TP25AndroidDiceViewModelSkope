package com.example.dice.viewmodel.scope

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.dice.viewmodel.scope.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val ONE = 1
const val SIX = 6

val diceImages: List<Int> by lazy {
    listOf(
        R.drawable.dice_face_one,
        R.drawable.dice_face_two,
        R.drawable.dice_face_three,
        R.drawable.dice_face_four,
        R.drawable.dice_face_five,
        R.drawable.dice_face_five,
    )
}

data class DiceGame(
    val firstDice: Int,
    val secondDice: Int,
    val thirdDice: Int
)


class DicesViewModel : ViewModel() {
    private val _dicesThrow = MutableLiveData<DiceGame>()
    val dicesThrow: LiveData<DiceGame> = _dicesThrow
    private fun randomDice(): Int = (ONE..SIX).random()
    fun onRollDiceClickButton() {
        viewModelScope.launch {
            for (j in 0..10) {
                delay(timeMillis = 150)
                _dicesThrow.value = DiceGame(randomDice(), randomDice(), randomDice())
            }
        }
    }


}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val images: List<ImageView> = listOf(
            binding.firstDiceImage,
            binding.secondDiceImage,
            binding.thirdDiceImage,
        )
        Log.d("dans le oncreate", "foo bar")
        val dicesViewModel: DicesViewModel = ViewModelProvider(this)
            .get(DicesViewModel::class.java)
        dicesViewModel.dicesThrow.observe(this) { dices: DiceGame ->
            images[0].setImageResource(diceImages[dices.firstDice - 1])
            images[1].setImageResource(diceImages[dices.secondDice - 1])
            images[2].setImageResource(diceImages[dices.thirdDice - 1])
            Log.d("voir", dices.toString())
        }
        binding.rollDicebutton.setOnClickListener {
            dicesViewModel.onRollDiceClickButton()
        }
        setContentView(binding.root)
    }
}