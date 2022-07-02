package policies

import engine.*

object singleWitchPolicy : Policy {
    override val name = "singleWitchPolicy"
    override fun policy(
        state: GameState,
        cardChoices: CardChoices
    ): Card? {
        return when(state.context) {
            ChoiceContext.ACTION -> cardChoices[0]
            ChoiceContext.TREASURE -> cardChoices[0]
            ChoiceContext.BUY -> {
                val goldCards: Int = state.currentPlayer.allCards.filter { it == Card.GOLD }.size
                val witchCards = state.currentPlayer.allCards.filter { it == Card.WITCH }.size
                val provinceCards = state.currentPlayer.allCards.filter { it == Card.PROVINCE }.size

                val witchLeft = state.board[Card.WITCH]!!
                val duchyLeft = state.board[Card.DUCHY]!!
                val estateLeft = state.board[Card.ESTATE]!!


                return if(state.currentPlayer.coins >= 8 && goldCards > 0) {
                    Card.PROVINCE
                } else if (state.currentPlayer.coins >= 5 && witchCards == 0 && witchLeft > 0) {
                    Card.WITCH
                } else if (state.currentPlayer.coins >= 5 && provinceCards < 4 && duchyLeft > 0) {
                    Card.DUCHY
                } else if (state.currentPlayer.coins >= 5 && provinceCards < 2 && estateLeft > 0) {
                    Card.ESTATE
                } else if (state.currentPlayer.coins >= 6) {
                    Card.GOLD
                } else if (state.currentPlayer.coins >= 3) {
                    Card.SILVER
                } else {
                    null
                }
            }
            ChoiceContext.MILITIA -> cardChoices[0]
            else -> throw NotImplementedError()
        }
    }
}

