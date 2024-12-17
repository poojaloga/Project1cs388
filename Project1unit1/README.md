# Android Project 1 - *Name of App Here*

Submitted by: Pooja Loganathan

Wordle is an android app that recreates a simple version of the popular word game [Wordle](https://www.nytimes.com/games/wordle/index.html).

Time spent: 10 hours spent in total

## Required Features

The following **required** functionality is completed:

- [ ] **User has 3 chances to guess a random 4 letter word**
- [ ] **After 3 guesses, user should no longer be able to submit another guess**
- [ ] **After each guess, user sees the "correctness" of the guess**
- [ ] **After all guesses are taken, user can see the target word displayed**

The following **optional** features are implemented:
- [ ] User sees a visual change after guessing the correct word (confetti)  
- [ ] User can tap a 'Reset' button to get a new word and clear previous guesses
- [ ] User will get an error message if they input an invalid guess
- [ ] User can see a 'streak' record of how many words they've guessed correctly.

The following **additional** features are implemented:
- [ ] Toast messages for invalid word, right/wrong word guessed, game reset
- [ ] Duplicate letters in word are kept track of
- - [ ] Once correct word is guessed or failure to guess right word, keyboard is automatically hidden
* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

< <img src='./assets/requirements.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

<!-- Replace this with whatever GIF tool you used! -->
- GIF created with [CloudConvert](https://cloudconvert.com/)

## Notes

Some difficulties I encountered were trying to get the confetti to work, changing some of the function
so that if duplicate letters were used, they would only say they were in the word the appropriate 
number of times and writing the function to check if the users inserted word is a valid one. 

## Resources
- [ConstraintLayout documentation](https://developer.android.com/training/constraint-layout)
- [Displaying Toasts](https://guides.codepath.com/android/Displaying-Toasts)

## License

    Copyright [2024] [Pooja Loganathan]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.