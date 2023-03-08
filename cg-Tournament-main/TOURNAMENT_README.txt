user -> create-tournament
user -> create - admin to the tournament

users -> raise player request to tournaments
admin -> accept or regect 


Testing of Admin-Player request:
--------------------------------
Create 4 users with the user data - Insertion - approach
--------------------------------------------------------
users - data - Insertion (Registration)
---------------------------------------

user-1
------
  {      
        "username" : "user1",
        "fullName" : "userfull1",
        "emailAddress" : "user1@gmail.com",
        "password" : "Users1@s",
        "isVerified" : true
  }

user-2
------
  {      
        "username" : "user2",
        "fullName" : "userfull2",
        "emailAddress" : "user2@gmail.com",
        "password" : "User2@",
        "isVerified" : true
  }

user-3
------
  {      
        "username" : "user3",
        "fullName" : "userfull3",
        "emailAddress" : "user3@gmail.com",
        "password" : "User3@",
        "isVerified" : true
  }

user-4
------
  {      
        "username" : "user4",
        "fullName" : "userfull4",
        "emailAddress" : "user8@gmail.com",
        "password" : "User4@",
        "isVerified" : true
  }

-----------------------------------------------



Now create a Tournament with the user
-------------------------------------

Tournament - creation - with - (headers)
----------------------------------------
enter postman header & insert key "username" & its value

Tournament Details:
Players only 4
Rounds only 2

{
    "id": 10007,
    "tournamentName": "Freshly Created Tournament",
    "competitionType": "No Signups Yet",
    "tournamentDate": "2021-10-22 21:30 PM UTC",
    "venue": "Testing5",
    "playerLimit": 4,

    "ownerUserId": #remember to change this number to user id, 

    "countPlayers": 0,
    "countRounds": 0,
    "gamesPerMatch": 1,
    "firstTiebreaker": 1,
    "secondTiebreaker": 0,
    "thirdTiebreaker": 0,
    "fourthTiebreaker": 0,
    "fifthTiebreaker": 0,
    "firstCustomTiebreakerName": "",
    "secondCustomTiebreakerName": "",
    "lowerBetterForFirstCustom": false,
    "lowerBetterForSecondCustom": false,
    "completed": false,
    "registrationOpen": true,
    "playerRegistrationOn": true
  }
--------------------------------------------------------


create admin with tournamentId & userId (2)
---------------------------------------
Note: Owner cannot be admin
---------------------------
use swagger / postman.
http://localhost:8080/swagger-ui.html#/player-controller
--------------------------------------------------------


now create a playe request using user3 & user4.
-----------------------------------------------
use swagger/postman
-----------------------------------------------

now check the request and do CRUD operations on the request as an admin
-----------------------------------------------------------------------





Testing Core Functionality
--------------------------
users - data - Insertion (Registration)
---------------------------------------

user-5
------
  {      
        "username" : "user5",
        "fullName" : "userfull5",
        "emailAddress" : "user5@gmail.com",
        "password" : "Users5@s",
        "isVerified" : true
  }

user-6
------
  {      
        "username" : "user6",
        "fullName" : "userfull6",
        "emailAddress" : "user6@gmail.com",
        "password" : "User6@",
        "isVerified" : true
  }

user-7
------
  {      
        "username" : "user7",
        "fullName" : "userfull7",
        "emailAddress" : "user7@gmail.com",
        "password" : "User7@",
        "isVerified" : true
  }

user-8
------
  {      
        "username" : "user8",
        "fullName" : "userfull8",
        "emailAddress" : "user8@gmail.com",
        "password" : "User8@",
        "isVerified" : true
  }

user-9
------
  {      
        "username" : "user9",
        "fullName" : "userfull9",
        "emailAddress" : "user9@gmail.com",
        "password" : "User9@",
        "isVerified" : true
  }


Tournament - creation - with - (headers)
----------------------------------------
enter postman & insert key "username" & value 5

Tournament Details:
Players only 4
Rounds only 2

{
    "id": 10006,
    "tournamentName": "Freshly Created Tournament",
    "competitionType": "No Signups Yet",
    "tournamentDate": "2021-10-22 21:30 PM UTC",
    "venue": "Testing5",
    "playerLimit": 4,
    "ownerUserId": 5,
    "countPlayers": 0,
    "countRounds": 0,
    "gamesPerMatch": 1,
    "firstTiebreaker": 1,
    "secondTiebreaker": 0,
    "thirdTiebreaker": 0,
    "fourthTiebreaker": 0,
    "fifthTiebreaker": 0,
    "firstCustomTiebreakerName": "",
    "secondCustomTiebreakerName": "",
    "lowerBetterForFirstCustom": false,
    "lowerBetterForSecondCustom": false,
    "completed": false,
    "registrationOpen": true,
    "playerRegistrationOn": true
  }

--------------------------
Players - data - Insertion
---------------------------
-----
user6
-----
{
  "confirmed": true,
  "displayName": "new-test-u-1",
  "dropped": false,

  "tournamentId": 10006,
  "userId": 6
}
-----
user7
-----
{
  "confirmed": true,
  "displayName": "new-test-u-2",
  "dropped": false,

  "tournamentId": 10006,
  "userId": 7
}
-----
user8
-----
{
  "confirmed": true,
  "displayName": "new-test-u-3",
  "dropped": false,

  "tournamentId": 10006,
  "userId": 8
}
-----
user9
-----
{
  "confirmed": true,
  "displayName": "new-test-u-4",
  "dropped": false,

  "tournamentId": 10006,
  "userId": 9
}
--------------------------



--------------------------
rounds - data - insertion
---------------------------

round 1 (roundId-9)
{

  "roundNumber": 1,
  "topCut": 2,
  "tournamentId": 10006
}
-------------------------
round 2(roundId-10)

{

  "roundNumber": 2,
  "topCut": 2,
  "tournamentId": 10006
}
--------------------------



----------------------------
matches - data - insertion
---------------------------

round-1 & match-1 (roundId-9)
-----------------
{
  "firstCustomFirstPlayer": 0,
  "firstCustomSecondPlayer": 0,
  "firstPlayerId": 37,
  "gameDrawsFirstPlayer": 0,
  "gameDrawsSecondPlayer": 0,
  "gameLossesFirstPlayer": 0,
  "gameLossesSecondPlayer": 0,
  "gameWinsFirstPlayer": 0,
  "gameWinsSecondPlayer": 0,
  "matchResultFirstPlayer": "WIN",
  "matchResultSecondPlayer": "LOSS",
  "roundId": 9,
  "secondCustomFirstPlayer": 0,
  "secondCustomSecondPlayer": 0,
  "secondPlayerId": 38,
  "tableNumber": 1
}


round-1 & match-2 (roundId-9)
------------------
{
  "firstCustomFirstPlayer": 0,
  "firstCustomSecondPlayer": 0,
  "firstPlayerId": 39,
  "gameDrawsFirstPlayer": 0,
  "gameDrawsSecondPlayer": 0,
  "gameLossesFirstPlayer": 0,
  "gameLossesSecondPlayer": 0,
  "gameWinsFirstPlayer": 0,
  "gameWinsSecondPlayer": 0,
  "matchResultFirstPlayer": "LOSS",
  "matchResultSecondPlayer": "WIN",
  "roundId": 9,
  "secondCustomFirstPlayer": 0,
  "secondCustomSecondPlayer": 0,
  "secondPlayerId": 40,
  "tableNumber": 1
}


round-2 & match-1 (roundId-10)
-------------------------------
{
  "firstCustomFirstPlayer": 0,
  "firstCustomSecondPlayer": 0,
  "firstPlayerId": 37,
  "gameDrawsFirstPlayer": 0,
  "gameDrawsSecondPlayer": 0,
  "gameLossesFirstPlayer": 0,
  "gameLossesSecondPlayer": 0,
  "gameWinsFirstPlayer": 1,
  "gameWinsSecondPlayer": 1,
  "matchResultFirstPlayer": "LOSS",
  "matchResultSecondPlayer": "WIN",
  "roundId": 10,
  "secondCustomFirstPlayer": 0,
  "secondCustomSecondPlayer": 0,
  "secondPlayerId": 40,
  "tableNumber": 2
}
