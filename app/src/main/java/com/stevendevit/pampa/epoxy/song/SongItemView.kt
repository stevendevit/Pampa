package com.stevendevit.pampa.epoxy.song

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.stevendevit.domain.model.Music
import com.stevendevit.music.MusicUtils
import com.stevendevit.pampa.R
import kotlinx.android.synthetic.main.item_music_row.view.*

/**
 * Created by stevendevit on 12/01/2020.
 * tankadeveloper@gmail.com
 */
@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT
)
class SongItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val durationView: TextView

    private lateinit var music: Music

    init {

        View.inflate(context, R.layout.item_music_row, this)
        titleView = (findViewById(R.id.title))
        durationView = (findViewById(R.id.duration))
    }

    @ModelProp
    fun model(model: Music) {

        this.music = model
    }

    @AfterPropsSet
    fun onPropsSet(){

        this.titleView.text = music.title
        this.duration.text = MusicUtils.formatSongDuration(music.duration, false)
    }
}