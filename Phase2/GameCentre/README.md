# Setup

* Install Android Studio

* * Checkout Project from Version Control

  * Git
  
  * Paste repo url
  
  * Pick a directory
  
  * Clone
  
  * Do NOT create a project
  
* * Open an existing Android Studio project

  * group_0475
  
  * Phase1
  
  * GameCentre
  
* Wait for build to finish

* * AVD Manager

  * Create Virtual Device
  
  * Pixel 2
  
  * Oreo:API Level 27:Android 8.1
  
  * Finish
  
* Run 'app' on Pixel 2

* Wait for app to open

# Functionalities

* Either sign up and login or login as a guest
  (if you're a guest you cannot save games and your highscores will have the tag "Guest")
  
* Select Sliding Tiles

* Input the number of undos you want to be allowed

* Pick a game size and play

* The game autosaves every move

* There is a score counter at the bottom of the screen, along with an undo button which can undo
  your previous moves (provided you have enough moves to undo), and a cheat button which swaps the
  bottom-right tile with the tile immediately to its left
  (this is because otherwise some games are unsolvable)
  
* If you do not finish a game you can continue it using the load button

* You can access the scoreboard via the scoreboard button or by winning a game.
  The scoreboard has the top 3 global and user scores for each of the three game sizes.

# Explaining implementation of methods

* The sign in and sign up work as intended. When the user does not have an account, there will be
  a toast redirecting to signup page

* When the signup is done, it will redirect to sign in and allow user to input their username and
  password

* When the login is successful, you will get redirected to the page where you can choose a game to
  play (We wrote the code, planning for Phase 2 so there will be two empty buttons for the other
  games that we intend to develop.

* When choosing sliding tiles, you will be redirected to a page where a new game is allowed to be
  started provided you give a number of moves that can be undone in the game.

* Load game button is there and if it is clicked when there is no saved game, it will provide an
  error saying that there are no saves

* The game has an autosave running in the background whenever a new move is made. This means when
  the game is quit, loading will bring you back to where you were.

* We removed the save button because most current mobile games have no save button and perform
  autosave for the users, and we believe that is a good implementation

* We included a cheat button that allows the swapping of two tiles in the bottom right so that the
  game can be solved (there are situations when games cannot be solved) and this prevents the user
  from being stuck on one game

* Our scoring system increases the counter whenever a move is made. The goal of our game is to use
  the minimum number of moves to complete the game

* When the game ends, the code moves onto the scoreboard page and is then displayed,
  and will display a scoreboard accross all users as well as a per user score.