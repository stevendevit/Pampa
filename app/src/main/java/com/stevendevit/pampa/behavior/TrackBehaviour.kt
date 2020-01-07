package com.stevendevit.pampa.behavior

/**
 * Created by stevendevit on 03/01/2020.
 * tankadeveloper@gmail.com
 */
class TrackBehaviour(
    private val cmd: String,
    private val seconds: Int
) : AbstractBehavior() {
    override fun perform() {

     /*   when (cmd) {
            "retroceder" -> mediaPlayerHolder.seekTimeBackTo(seconds)
            "avanzar" -> mediaPlayerHolder.seekTimeTo(seconds)
        }*/
    }
}
