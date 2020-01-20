package com.stevendevit.pampa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearSmoothScroller
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.EpoxyVisibilityTracker
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerThumbView
import com.reddit.indicatorfastscroll.FastScrollerView
import com.stevendevit.music.songs.ISongDataSource
import com.stevendevit.pampa.R
import com.stevendevit.pampa.epoxy.MusicLibraryModelController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val musicLibraryController by lazy { MusicLibraryModelController() }
    private val songDataSource: ISongDataSource by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val epoxyRecyclerView = root.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        val epoxyVisibilityTracker = EpoxyVisibilityTracker()
        epoxyVisibilityTracker.attach(epoxyRecyclerView)
        epoxyRecyclerView.setController(musicLibraryController)

        val fastScrollerView = root.findViewById<FastScrollerView>(R.id.fastScrollerView)
        val fastScrollerThumbView =
            root.findViewById<FastScrollerThumbView>(R.id.fastScrollerThumbView)

        fastScrollerThumbView.apply {

            setupWithFastScroller(fastScrollerView)
        }

        fastScrollerView.apply {

            setupWithRecyclerView(
                epoxyRecyclerView,
                { position ->

                    println(position)

                    val model = musicLibraryController.currentData?.get(position)

                    model?.let {

                        FastScrollItemIndicator.Text(
                            model
                                .title?.substring(0, 1)?.toUpperCase() ?: ""
                        )
                    }
                }, useDefaultScroller = false
            )
        }

        val smoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        }

        fastScrollerView.itemIndicatorSelectedCallbacks += object : FastScrollerView.ItemIndicatorSelectedCallback {
            override fun onItemIndicatorSelected(
                indicator: FastScrollItemIndicator,
                indicatorCenterY: Int,
                itemPosition: Int
            ) {
                epoxyRecyclerView.stopScroll()
                smoothScroller.targetPosition = itemPosition
                epoxyRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            val songs = songDataSource.getAllSongs()

            songs?.let {

                musicLibraryController.setData(it)
            }
        }

//        songDataSource.getAllSongs()
    }
}