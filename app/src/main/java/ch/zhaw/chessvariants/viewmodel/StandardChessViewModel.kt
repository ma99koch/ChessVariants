package ch.zhaw.chessvariants.viewmodel

import ch.zhaw.chessvariants.backend.StandardChess

class StandardChessViewModel(private val fen: String) : ChessViewModel() {

    init {
        chess = StandardChess(fen)
        chess.initBoard()
        updateStateFromBackend()
    }

}