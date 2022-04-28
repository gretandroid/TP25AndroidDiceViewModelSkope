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
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5
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

fun List<Int>.imageViewId(): Int = if (isEmpty())
    throw NoSuchElementException("dice throw is empty.")
else elementAt(index = 1)

data class DiceGame(
    val firstDice:Int,
    val secondDice:Int,
    val thirsDice:Int
)


class DicesViewModel : ViewModel() {
    private val _dicesThrow = MutableLiveData<Pair<Int, Int>>()
    val dicesThrow: LiveData<Pair<Int, Int>> = _dicesThrow
    private fun randomDice(): Int = (ONE..SIX).random()
    fun onRollDiceClickButton() {
        for (i in 0..2) {
            viewModelScope.launch {
                for (j in 0..10) {
                    delay(timeMillis = 150)
                    _dicesThrow.value = Pair(i, randomDice())
                }
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
        Log.d("dans le oncreate","foo bar")
        val dicesViewModel: DicesViewModel = ViewModelProvider(this)
            .get(DicesViewModel::class.java)
        dicesViewModel.dicesThrow.observe(this) { dices: Pair<Int, Int> ->
            images[dices.first].setImageResource(diceImages[dices.second-1])
            Log.d("voir", dices.toString())
        }
        binding.rollDicebutton.setOnClickListener {
            dicesViewModel.onRollDiceClickButton()
        }
        setContentView(binding.root)
    }
}