package ch.zhaw.chessvariants.viewmodel

import gameTypes.chess.Chess960

class Chess960ViewModel : ChessViewModel() {

    init {
        chess = Chess960()
        chess.initGame()
        updateStateFromBackend()
    }

}