
---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: SuperAlphaGo
* Team members
  * JUSTIN YAU
  * KYLE MCCOY
  * BRIAN MIRABITO
  * LUYANG LIN

## Executive Summary

WebCheckers is a web based application that allows users to play a functional game of
checkers with other signed-in players. The project utilizes the Spark Micro Web framework and FreeMarker Template Engine.
The high level architecture of the project consists of the UI, Model, and Application tiers.
These tiers provide a logical separation of responsibility. The Agile development cycle was used to gradually
implement the different functionalities of the MVP and related enhancements.

### Purpose
The WebCheckers web application aims for users to play a fully functional game of checkers in a browser.
This game of checkers will adhere to American Rules.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| VO | Value Object |
| MVP | Minimal Viable Product |
| UI | User Interface |


## Requirements

This section describes the features of the application.

### Definition of MVP

The MVP is an web application where users can sign in, choose an opponent, and then play a game of Web Checkers adhering
to the standard American Rules of Checkers. Players in-game user may choose to resign at any time which will end the game
early.

### MVP Features

1.  **Sign In** Every player must sign-in before playing a game, and be able to sign-out when finished playing.
2.  **Gameplay** This epic ensures the full functionality required for two players to be able to play a game of checkers
based upon the  [American rules](http://www.se.rit.edu/~swen-261/projects/WebCheckers/American%20Rules.html).
3.  **Resignation** Either player of a game may choose to resign, at any point, which ends the game.
4.  **Validate Move** This epic ensures that moves are validated to be legal before being finalized by the application.

### Roadmap of Enhancements
The enhancements of interest to Team D in order of preference are:

1.  _Spectator Mode_: Other players may view an on-going game that they are not playing.
2.  _Player Help_: Extend the Game View to support the ability to request help.


## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](domain-model.png)
_Figure 1: Domain analysis of the Web Application_

###  Overview of the domain

1. Players log into the web application using the login interface to then be able to start a checkers game with other players.
2. The CheckersGame contains a state of the board which contains rows of spaces of white/black colors and can be occupied by
pieces of red/black colors.
3. Each Piece can be a normal piece or a king piece.
4. Each player that is in-game would be able to make a move and update the state of the CheckersGame accordingly.
5. A player can be spectating, to which they would be unable to make a move in a game, or a actual player, who would be able to play the game.
6. A player can use a help interface to assist with the game.
7. The CheckersGame adheres to the rules defined by the American Rules.

## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)
_Figure 2: Diagram of the WebCheckers Architectural Structure_

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](state-diagram.png)
_Figure 3: A State Chart that represents the different UI Views_

### Flow of Pages

A first time user would be welcomed to the homepage. From there this user can navigate to the sign-in page
and sign in using a username. Given the username is legal and not in the system already, the user will be
redirected back to the home page where they can view other users to start a game with. From the time that the
user is signed-in, there is a session timeout watchdog listening for inactivity and will automatically log the user
out and if they come back, they would be redirected to the homepage and prompted as if they were a first-time user.
If the user starts a game, they would be directed to the game view and once that game ends, they would have the
option to navigate back to the homepage or sign out and be redirected to the homepage.

### UI Tier
The components of the UI Tier works to provide an interface to view content and provide input to users.
Contents of the UI Tier is often pulled from other tiers. Sometimes, conversion of pulled data
must be performed. The UI tier is responsible for this conversion as well as handling requests by the user.

The UI Tier of the WebCheckers project consists of several components that can be classified into the following:

AjaxRoutes: These routes enable the client's browser to exchange information with the server without refreshing during
gameplay. Not refreshing helps to ensure a smoother experience where the client is not forced to load a new page for
game actions, like validating a move, checking the turn.

*  PostCheckTurnRoute
*  PostResignGameRoute

HTTPRoutes: Unlike the Ajax routes, routes under this classification must be re-rendered each time. These HTTP routes are
responsible for displaying views (a GET request) and updating data from the client (a POST request).

* GetGameRoute
* GetHomeRoute
* GetSigninRoute
* PostSignoutRoute
* PostSigninRoute
* WebServer


### Application Tier
The Application Tier manages logic and state of information of the general application as a whole.
It provides client-specific services to the UI Tier so that it can generate the proper views.

This tier in our application consists of the GameCenter and PlayerLobby components. GameCenter keeps a state of all
active games and players in-game along with their opponents. This will prove crucial when it comes to determining if
a player is in-game or not and retrieving game state. Handles to start and end a game are also a part of this component.
PlayerLobby keeps a state of all signed-in players and determines legality of the usernames of the users trying to sign in.
Handles to log in and log out are a part of this component. All of these components formats and provides their respective
information to the UI Tier so it can generate the proper view.

Here is a UML Diagram that illustrates how the classes in the Application Tier work together:

![Application Tier UML](ApplicationTierUML.png)

### Model Tier
The Model Tier contains components where it holds actual information pertaining to the state of the board. These components
will be updated to update the state of the board with the aid of helper functions to translate information to the UI tier.
This tier's main responsibility is to store the representations of the Board, Space, and Piece for the game.

![The WebCheckers Structural Class Diagram for Model Tier](model-tier-structural-class-model.png)
_Figure 4: Structural Class Diagram for Model Tier_

The Board component stores the state of the red player's board and white player's board. This will be primarily used by the game
view to retrieve the representation of the boards at a certain period of time to be displayed.

The Piece component stores information regarding what color it is so that players can identify which are theirs to move.
This information is also used by the other tiers to determine if a move is legal.

The Space component stores the state of a space on the board. It can be occupied by a piece and keeps track of colors. This
will be used to display each specific space on the board.

There is also a player model component that represents a player. This component's primary responsibility is to store and
return the username of a player. It will be primarily used by the PlayerLobby application tier component to display rosters
and determine lobby size.

## System: Player Sign-In and Player Sign-Out

This section will describe the functionality of the Player Sign-In/Sign-Out.

### Purpose of system

This system aims to allow the players to sign into the web application with a unique username that allows other users
to identify them with. Users can enter a username which will be checked to not match any online players' usernames and
with success, they would be able to play a game of checkers adhering to American Rules. With username input failures,
the player would be prompted to enter another username after being notified of the error. Users can then log out at any
point to which the system would handle all necessary clean up to allow another user to log in with the same username.

### Static Models

![Static Model for Login System](login-system-static-model.png)
_Figure 5: Static Model for Login System_

### Dynamic Models

![Sequence Diagram for Login System](login-system-sequence-model.png)
_Figure 6: Sequence Diagram for Login System_

## System: Start Game

This section will describe the functionality of how we start a checkers game.

### Purpose of system

This system aims to allow the player to start a checkers game by clicking on the name of another user in the player lobby. When the user does this, on success, the user will be launched into the game route and be shown a board with the user as the white player and the opposing player as the red player. They will then be able to play a game of checkers adhering to American Rules. If one of the players is in a game or leaves the lobby, then the game route will redirect the user back to the home screen. The game route will also redirect the user back to the home page if they click the home button inside the game view.

### Static Models

![Static Model for Game System](GameSystemStatic.png)

_Figure 7: Static Model for Game System_

### Dynamic Models

![Sequence Diagram for Game System](game-system-sequence-model.png)
_Figure 8: Sequence Diagram for Game System_

### Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements. After completion of the Code metrics exercise, you
> will also discuss the resutling metric measurements.  Indicate the
> hot spots the metrics identified in your code base, and your
> suggested design improvements to address those hot spots._

## Testing

This section will provide information about the testing performed
and the results of the testing._

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
For unit testing, we tried to achieve full coverage for each of the classes that we wrote unit tests for. 
We tried accounting for all possible cases when it comes to testing to ensure that our web application
is bug-free. Currently there are some issues with getting coverage after a route redirect. We've tried
checking that the route returns null but that proved ineffective. If there's a solution that we discover later down the
line, we will implement that towards all applicable routes. 
