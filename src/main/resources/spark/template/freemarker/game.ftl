<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
  <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script>
  window.gameData = {
    "gameID" : ${gameID!'null'},
    "currentUser" : "${currentUser.name}",
    "viewMode" : "${viewMode}",
    "modeOptions" : ${modeOptionsAsJSON!'{}'},
    "redPlayer" : "${redPlayer.name}",
    "whitePlayer" : "${whitePlayer.name}",
    "activeColor" : "${activeColor}"
  };
  </script>
</head>
<body>
<div class="page">
  <h1>Web Checkers | Game View</h1>

  <#include "nav-bar.ftl" />

  <div class="body">

    <div id="help_text" class="INFO"></div>

    <div>
      <div id="game-controls">

        <fieldset id="game-info">
          <legend>Info</legend>

          <#include "message.ftl" />

          <div>
            <table data-color='RED'>
              <tr>
                <td><img src="../img/single-piece-red.svg" /></td>
                <td class="name">Red</td>
              </tr>
            </table>
            <table data-color='WHITE'>
              <tr>
                <td><img src="../img/single-piece-white.svg" /></td>
                <td class="name">White</td>
              </tr>
            </table>
          </div>
        </fieldset>

        <fieldset id="game-toolbar">
          <legend>Controls</legend>
          <div class="toolbar"></div>
          <button id="get-help" onclick="on()" type="submit">HELP</button>
        </fieldset>

      </div>

      <div class="game-board">
        <table id="game-board">
          <tbody>
          <#list board.iterator(invertedView) as row>
          <tr data-row="${row.index}">
            <#list row.iterator() as space>
            <td data-cell="${space.cellIdx}"
            <#if space.isValid() >
            class="Space"
          </#if>
          >
          <#if space.piece??>
          <div class="Piece"
               id="piece-${row.index}-${space.cellIdx}"
               data-type="${space.piece.type}"
               data-color="${space.piece.color}">
          </div>
          </#if>
          </td>
        </#list>
        </tr>
      </#list>
      </tbody>
      </table>
    </div>
  </div>
    <div id="background" onclick="off()"></div>
    <div id="overlay">
      <body>
      <h1>Help Menu <button class="overlayright" onclick="off()">X</button></h1>
        <div class="overlaycontainer">
          <div class="overlaytitle"> Interface Help </div>
          <div class="overlayanchor">
            <ul class="overlaylist">
              <li> - Drag and drop pieces to make a move when it's your turn. Move legality is checked automatically. </li>
              <li> - Press the resign button if you would like to resign from the game. </li>
              <li> - Press the submit turn button when you are satisfied with your last legal move. </li>
              <li> - Press the backup button if you would like to undo your last legal move and make a different move. </li>
              <li> - Short on time? You can sign out at any time by pressing the sign-out found in the navigation bar. </li>
              <li> - You can click anywhere outside the box to exit the help interface. </li>
            </ul>
          </div>
          <div class="overlaytitle"> Standard American Checkers Game Rule </div>
          <a class="overlayanchor" href="https://www.itsyourturn.com/t_helptopic2030.html" target="_blank">https://www.itsyourturn.com/t_helptopic2030.html</a>
          <div class="overlaytitle"> Helpful Videos for Advanced Tricks </div>
          <a class="overlayanchor" href="https://www.youtube.com/watch?v=Lfo3yfrbUs0" target="_blank">https://www.youtube.com/watch?v=Lfo3yfrbUs0</a> <br />
          <a class="overlayanchor" href="https://www.youtube.com/watch?v=-O2MiBpoFNc" target="_blank">https://www.youtube.com/watch?v=-O2MiBpoFNc</a>
        </div>
      </body>
    </div>

    <style>

      .overlayright {
        float: right;
        color: black;
        width: 10px;
        height: 10px;
        border: none;
        outline: 0;
        background: rgba(0,0,0,0);
        padding: 0;
        cursor: pointer;
      }

      .overlayright:active, .overlayright:focus {
        outline: 0;
      }

      .overlaylist {
        list-style-type: none;
        padding: 0;
      }

      .overlaycontainer {
        padding: 10px;
      }

      .overlaytitle {
        color: black;
        font-weight: 600;
        font-size: 18px;
        padding-top: 5px;
        padding-bottom: 5px;
      }

      .overlayanchor {
        color: black;
        text-decoration: none;
        font-size: 15px;
      }

      #background {
        position: fixed;
        display: none;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.75);
        z-index: 10;
      }

      #overlay {
        position: absolute;
        display: none;
        width: 50%;
        top: 25%;
        left: 25%;
        right: 0;
        bottom: 0;
        color: black;
        cursor: default;
        background-color: white;
        z-index: 1000;
      }

    </style>
</div>
</div>

<audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>

<script data-main="/js/game/index" src="/js/require.js"></script>
<script>
  function on(){
    document.getElementById("background").style.display="block";
    document.getElementById("overlay").style.display="block";
  }
  function off(){
    document.getElementById("overlay").style.display="none";
    document.getElementById("background").style.display="none";
  }
</script>

</body>
</html>