<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
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
          <button id="get-help" onclick="on()" type="submit">GET-HELP</button>
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
    <div id="overlay" onclick="off()">
      <button id="close" onclick="off()" type="submit">CLOSE</button>
      <body>
      <h1>Please refer to the following website for the
      standard American checkers game rule.</h1>
      <a href="https://www.itsyourturn.com/t_helptopic2030.html" target="_blank"><font color="white"> https://www.itsyourturn.com/t_helptopic2030.html</font></a>
      <h1>Following links contains helpful videos for advanced tricks</h1>
      <a href="https://www.youtube.com/watch?v=Lfo3yfrbUs0" target="_blank"><font color="white">https://www.youtube.com/watch?v=Lfo3yfrbUs0</font></a>
      <p></p>
      <a href="https://www.youtube.com/watch?v=-O2MiBpoFNc" target="_blank"><font color="white">https://www.youtube.com/watch?v=-O2MiBpoFNc</font></a>
      </body>
    </div>

    <style>
      #overlay {
        position: fixed;
        display: none;
        width: 50%;
        height: 50%;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(0,0,0,0.9);
        z-index: 2;
        cursor: pointer;
      }
    </style>
</div>
</div>

<audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>

<script data-main="/js/game/index" src="/js/require.js"></script>
<script>
  function on(){
    document.getElementById("overlay").style.display="block";
  }
  function off(){
    document.getElementById("overlay").style.display="none";
  }
</script>

</body>
</html>