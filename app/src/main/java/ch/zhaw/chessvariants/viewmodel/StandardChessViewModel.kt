package ch.zhaw.chessvariants.viewmodel

import ch.zhaw.chessvariants.backend.StandardChess

class StandardChessViewModel : ChessViewModel() {

    init {
        chess = StandardChess()
        chess.initGame()
        updateStateFromBackend()
    }

}