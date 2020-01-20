package com.stevendevit.pampa.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.stevendevit.domain.model.Music
import com.stevendevit.pampa.epoxy.song.songItemView

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class MusicLibraryModelController : TypedEpoxyController<List<Music>>() {
    override fun buildModels(data: List<Music>) {

        data.mapIndexed { i, item ->
            songItemView {
                id(i)
                model(item)
            }
        }
    }
}