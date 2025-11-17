# Team Project: VocabVault

Please keep this up-to-date with information about your project throughout the term.

The readme should include information such as:
- a summary of what your application is all about
- a list of the user stories, along with who is responsible for each one
- information about the API(s) that your project uses 
- screenshots or animations demonstrating current functionality

## **Domain**

Our project’s domain is Education, with the main purpose being a language learning app that provides support across 189 languages. This app allows users to study languages from the flashcard decks through active recall and provides robust translations of words from a source language to a target language, using the Google Cloud Translation API’s machine learning algorithms.

### User Stories
- User Story #1: As a user I want to create flashcards for new vocabulary I want to learn.
- User Story #2: As a user, I want to study a deck by flipping cards manually so I can have a chance to recall before seeing the answer.
- User Story #3: As a user I want to be able to view a graph of my weekly learning progress in order to reflect on my language learning.
- User Story #4: As a user I want to be able to set and modify learning goals (e.g. I wanna learn X new words a day).
- User Story #5: As a user I want to be able to group flashcards into decks (with tags) in order to study words in context.
- User Story #6: As a user I want to be able to sign up for an account so my progress is saved.
- User Story #7: As a user I want to be able to login in order to view my saved progress and flashcards.
- User Story #8: As a user I want to mark the words I don’t know so I can review them later.
- User Story #9: As a user, I want to be able to edit and delete existing flashcards so I can correct mistakes or remove ones I don’t need.
- User Story #10: As a user, I want to see all my decks listed on the deck menu, and there’s a Don’t Know Deck on the top
- User Story #11: As a user, I want to open each deck on the deck menu, and on each deck’s panel, I can see all the flashcards of the deck and can do further execution of adding flashcards (UC#1), studying the flashcards (UC#2), and editing flashcards (UC#9)
- User Story #12: As a user, I want to be able to change my password.
- User Story #13: As a user, I want to be able to log out of my account.

## **Use Cases**

### Use Case #1 Title: Flashcard Creation

Related User Story #1: As a user I want to be able to create flashcards for new vocabulary I want to learn.

Main Flow:
In the deck menu, user clicks on the deck they want to add flashcards to.
User clicks create flashcard button.
User types the word they want to learn in a chosen source language and chooses the target language they want the word to be translated into.
User clicks translate.
System translates the word for the user automatically and displays the translated word.
User clicks add to create the flashcard.


### Use Case #2 Title: Study a Deck of Flashcards

Related User Story #2: As a user, I want to study a deck by flipping cards manually so I can have a chance to recall before seeing the answer.

Main Flow:
User clicks on the decks menu and selects a deck to study from.
User clicks the play button.
User can choose to initially view the flashcards in the source language or target language.
System displays the chosen side of the flashcard.
The user then recites the word in the other language that’s not being displayed on the front of the flashcard in their head.
The user clicks the flashcard to view the back of the flashcard and check their answer.

### Use Case #3 Title: Display Learning Progress

Related User Story #3: As a user I want to be able to view a graph of my learning progress.

Main Flow:
The user clicks on the activity menu.
The system displays a histogram of the user’s daily goal completion rate for the week (for instance, if the user studied 10 flashcards out of their daily goal of 20 flashcards, they will get a 50% daily goal completion rate).
The system will display a new histogram every week.

### Use Case #4 Title: Setting Daily Learning Goals  

Related User Story #4: As a user I want to be able to set and modify learning goals (e.g. I wanna learn X new words a day).

Main Flow:
User clicks on the activity menu.
System displays their weekly activity progress (which will initially display 0% for all days when the user has not set any goals).
User clicks the set goals button below the activity chart.
User clicks the edit icon to modify their existing daily flashcard quota goal.

Alternate Flow:
User has not set any goals -> System displays 0% on the histogram for all days.
User has not set any goals -> User initializes their daily learning goals.

### Use Case #5 Title: Grouping Flashcards Into Decks

Related User Story #5: As a user I want to be able to group flashcards into decks (with tags) in order to study words in context.

Main Flow:
User clicks on the deck menu.
User clicks on the create decks button.
System prompts user to create a deck.
User types in deck title.
User clicks create deck button to create the deck.

### Use Case #6 Title: Sign Up

Related User Story #6: As a user I want to be able to sign up for an account so my progress is saved.

Main Flow:
System prompts the user to sign up when they first open the app.
System displays a registration form for the username, email, password, and confirmed password for the new account.
User fills the registration form.
User clicks the sign up button.   
System checks if username is available.
User account is created and stored in the system’s database.

Alternate Flow:
Username is not available -> System prompts user to select a different username.

### Use Case #7 Title: Log In

Related User Story #7: As a user I want to be able to login in order to view my saved progress and flashcards.

Main Flow:
System prompts the user to sign up when they first open the app.
System displays “Already have an account?” with a log in button below it on the sign up page.
User clicks log in button.
System prompts the user for their username and password for their account.
User fills out their account username and password.
User clicks the log in button.
System checks if the account is in the system database.
System sends user to their account’s home page.

Alternate Flow:
Username is not in the database -> System prompts user to re-enter their username or password.

### Use Case #8 Title: Marking Unknown Words

Related User Story #2: As a user I want to mark the words I don’t know so I can review them later.

Main Flow:
System displays a flashcard during a study session.
User attempts to recall the other side.
User clicks to flip over the flashcard.
User recalls the right answer and clicks a checkmark button to mark it as correct.

Alternate Flow:
User chose the wrong answer -> User clicks an X button to mark it as incorrect. System adds this flashcard to the back of the stack to review again while they are studying.

### Use Case #9 Title: Edit and Delete Existing Flashcards

Related User Story #1, User Story #9.

Main Flow:
The user clicks on the deck menu and selects a flashcard.
The system displays the flashcard’s current data.
The user edits the desired fields.
User clicks a button to save.
System saves the changes.
Alternative Flow:
User clicks delete button
System prompts for confirmation.
User confirms, and the system removes the flashcard from the deck.
Use Case #10 Title: List all Decks of the User on the Decks Menu
Related User Story #10: As a user, I want to see all my decks listed on the deck menu, and there’s a Don’t Know Deck on the top
The user opens the deck menu
System display all the user’s deck in a grid layout on the panel.
The Don’t Know Deck is automatically created if the user is newly signed in and is displayed first on the panel.
The user clicks on each deck to get into a detailed deck panel (UC#11).
The user clicks on the Home button to go back to the home page.

### Use Case #11 Title: Open Decks

Related User Story #11: As a user, I want to open each deck on the deck menu, and on each deck’s panel, I can see all the flashcards of the deck and can do further execution of adding flashcards ( UC#1), studying the flashcards (UC#2), and editing flashcards (UC#9)
The user clicks on a specific deck in the deck menu
System navigates to the Deck Detail panel.
System displays: deck title, list of flashcards (preview), and buttons: Deck Menu, +New Flashcard (UC#1), Play (UC#2)
The user clicks each flashcard to edit it (UC#9)

### Use Case #12 Title: Change Password

Related User Story #11: As a user, I want to be able to change my password.

Main Flow: 
The user enters their username and password. 
The user clicks on the login button. 
The user inputted a wrong password, so a "wrong password" error shows up. 
The system prompts the user to try again or change passwords. 

### Use Case #13 Title: Log Out

Related User Story #11: As a user, I want to be able to log out of my account.

Main Flow:
The user is on the logged in view.
The user clicks on the log-out button on the navigation bar.
The system asks the user if they are sure they want to log out. 
The user clicks yes. 
The system switches the logged in view to the login view. 

## Leads for Use Cases

### Jane

Use Case #6: Sign Up,
Use Case #7: Log In,
Use Case #11: Open Decks
User Story #12: Change Password
User Story #13: Log Out

### Jennifer

Use Case #2
Use Case #8
User Story #2
User Story #8

### Krista

Use Case #5: Create Deck
Use Case #10: List Deck
Use Case #11: Open Deck
User Story #5 #10 #11

### Mei

Use Case #3,
Use Case #4:
User Story #3
User Story #4

### Anna

Use Case #1: Add Flashcard
User Story #1

### Hannah

Use Case #9: Edit/Del Flashcard
User Story #9


## API for the Project

Google Cloud Translation API: https://cloud.google.com/translate?hl=en
This API offers robust machine learning translation capabilities for a wide range of media, across 189 languages. 
The API allows us to translate a quote from a source language (optional since the API can detect source languages) to a 
target language. We can also create our own glossaries to translate proper nouns and product names into another language
in a custom glossary.csv file. 

## Current Functionality Screenshots

To be added