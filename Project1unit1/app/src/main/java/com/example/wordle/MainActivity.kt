package com.example.wordle

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Toast
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import com.github.jinatonic.confetti.CommonConfetti
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.jinatonic.confetti.ConfettiManager

class MainActivity : AppCompatActivity() {

    var confettiManager: ConfettiManager? = null
    var attempts = 0
    var streakcount = 0
    var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",").map { it.uppercase() }
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val rootLayout = findViewById<ViewGroup>(R.id.main)
        val resetbutton = findViewById<Button>(R.id.resetButton)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            var guess1 = findViewById<TextView>(R.id.text7)
            var attempt1 = findViewById<TextView>(R.id.text8)
            var guess2 = findViewById<TextView>(R.id.text9)
            var attempt2 = findViewById<TextView>(R.id.text10)
            var guess3 = findViewById<TextView>(R.id.text11)
            var attempt3 = findViewById<TextView>(R.id.text12)
            val keyboard = findViewById<EditText>(R.id.Guess)
            var correctword = findViewById<TextView>(R.id.text13)
            var correctword1 = findViewById<TextView>(R.id.text14)
            var streak = findViewById<TextView>(R.id.text15)
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            resetbutton.setOnClickListener {
                resetGame(guess1, attempt1, guess2, attempt2, guess3, attempt3, keyboard, correctword, correctword1, resetbutton)
            }
        keyboard.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                val userGuess = keyboard.text.toString().uppercase()

                if (userGuess !in FourLetterWordList.getAllFourLetterWords()) {
                    Toast.makeText(this, "$userGuess is not in word list", Toast.LENGTH_SHORT).show()
                    keyboard.text.clear()
                    return@setOnEditorActionListener true
                }
                if (attempts == 0) {
                    guess1.text = userGuess
                    attempt1.text = checkGuess(guess1.text.toString())
                    if (attempt1.text == wordToGuess) {
                        streakcount++
                        streak.text = streakcount.toString()
                        confettiManager = CommonConfetti.rainingConfetti(rootLayout, intArrayOf(Color.RED, Color.BLUE, Color.GREEN)).infinite()
                        Toast.makeText(this, "Congrats, guessed right word!", Toast.LENGTH_SHORT).show()
                        correctword.text = correctword.text.toString() + wordToGuess
                        correctword.visibility = TextView.VISIBLE
                        hideKeyboard()
                        keyboard.visibility = EditText.INVISIBLE
                        resetbutton.visibility = Button.VISIBLE

                    }
                    else {
                        Toast.makeText(this, "Incorrect, try again", Toast.LENGTH_SHORT).show()
                    }
                } else if (attempts == 1) {
                    guess2.text = userGuess
                    attempt2.text = checkGuess(guess2.text.toString())
                    if (attempt2.text == wordToGuess) {
                        streakcount++
                        streak.text = streakcount.toString()
                        confettiManager = CommonConfetti.rainingConfetti(rootLayout, intArrayOf(Color.RED, Color.BLUE, Color.GREEN)).infinite()
                        Toast.makeText(this, "Congrats, guessed right word!", Toast.LENGTH_SHORT).show()
                        correctword.text = correctword.text.toString() + wordToGuess
                        correctword.visibility = TextView.VISIBLE
                        resetbutton.visibility = Button.VISIBLE
                        hideKeyboard()
                        keyboard.visibility = EditText.INVISIBLE
                    }
                    else {
                        Toast.makeText(this, "Incorrect, try again", Toast.LENGTH_SHORT).show()
                    }
                } else if (attempts == 2) {
                    guess3.text = userGuess
                    attempt3.text = checkGuess(guess3.text.toString())
                    if (attempt3.text == wordToGuess) {
                        streakcount++
                        streak.text = streakcount.toString()
                        confettiManager = CommonConfetti.rainingConfetti(rootLayout, intArrayOf(Color.RED, Color.BLUE, Color.GREEN)).infinite()
                        Toast.makeText(this, "Congrats, guessed right word!", Toast.LENGTH_SHORT).show()
                        correctword.text = correctword.text.toString() + wordToGuess
                        correctword.visibility = TextView.VISIBLE
                        hideKeyboard()
                        keyboard.visibility = EditText.INVISIBLE
                        resetbutton.visibility = Button.VISIBLE
                    }
                    else {
                        streakcount = 0
                        streak.text = streakcount.toString()
                        Toast.makeText(this, "Failure, word was $wordToGuess", Toast.LENGTH_SHORT).show()
                        correctword1.text = correctword1.text.toString() + wordToGuess
                        correctword1.visibility = TextView.VISIBLE
                        hideKeyboard()
                        keyboard.visibility = EditText.INVISIBLE
                        resetbutton.visibility = Button.VISIBLE
                    }
                }
                keyboard.text.clear()
                attempts +=1
                true
            }
            else {
                false
            }
        }
            insets
        }
    }

    private fun checkGuess(guess: String): String {
        var result = ""

        // Create a mutable list of the characters in the word to guess
        val availableLetters = wordToGuess.toMutableList()

        // First pass: Check for correct positions (exact matches)
        for (i in guess.indices) {
            if (guess[i] == wordToGuess[i]) {
                result += guess[i] // Correct letter in the correct position
                availableLetters[i] = '*'  // Mark this letter as used
            } else {
                result += " "  // Placeholder for now
            }
        }
        // Second pass: Check for correct letters in the wrong positions
        for (i in guess.indices) {
            if (result[i] == ' ') {  // Skip already matched letters
                if (guess[i] in availableLetters) {
                    result = result.substring(0, i) + "+" + result.substring(i + 1)
                    availableLetters[availableLetters.indexOf(guess[i])] = '*'  // Mark this letter as used
                } else {
                    result = result.substring(0, i) + "X" + result.substring(i + 1)  // Wrong letter
                }
            }
        }
        return result
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = this.currentFocus ?: View(this)  // Use current focused view or fallback to a new view
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun resetGame(
        guess1: TextView, attempt1: TextView,
        guess2: TextView, attempt2: TextView,
        guess3: TextView, attempt3: TextView,
        keyboard: EditText, correctword: TextView, correctword1: TextView,
        resetButton: Button
    ) {
        confettiManager?.terminate()
        confettiManager = null
        // Reset the text fields
        guess1.text = "Guess"
        attempt1.text = "- - - -"
        guess2.text = "Guess"
        attempt2.text = "- - - -"
        guess3.text = "Guess"
        attempt3.text = "- - - -"
        correctword.text = "Congrats Word Was: "
        correctword1.text = "Failure Word Was: "
        correctword.visibility = TextView.INVISIBLE
        correctword1.visibility = TextView.INVISIBLE

        // Reset the input field and attempts
        keyboard.visibility = EditText.VISIBLE
        keyboard.text.clear()
        attempts = 0

        // Hide the reset button
        resetButton.visibility = Button.INVISIBLE

        // Generate a new word
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        Toast.makeText(this, "Game reset", Toast.LENGTH_SHORT).show()
    }
}

