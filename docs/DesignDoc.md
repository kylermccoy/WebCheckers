
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

### MVP Features
The MVP is an web application where users can sign in, choose an opponent, and then play a game of Web Checkers adhering
to the standard American Rules of Checkers. Players in-game user may choose to resign at any time, ending the game early.

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

#  Overview of the domain

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

> _Provide a summary of the application's user interface.  Describe, from
> the user's perspective, the flow of the pages in the web application._


### UI Tier
> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._


### Application Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._


### Model Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._

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
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._