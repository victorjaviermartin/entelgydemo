package com.victormartin.ownpractice.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.victormartin.ownpractice.R
import com.victormartin.ownpractice.databinding.ActivityMainBinding
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel
import com.victormartin.ownpractice.ui.base.BaseActivity
import com.victormartin.ownpractice.ui.details.DetailsActivity
import com.victormartin.ownpractice.ui.main.adapter.HeroesListAdapter
import com.victormartin.ownpractice.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), HeroesListAdapter.OnItemClickListener {

    private lateinit var mAdapter: HeroesListAdapter

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override val mViewModel: MainViewModel by viewModel()

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)  // Set AppTheme before setting content view.

        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        mAdapter = HeroesListAdapter(this)

        // Initialize RecyclerView
        mViewBinding.heroesListRecycler.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = mAdapter
        }

        initHeroes()

        handleNetworkChanges()
    }

    private fun initHeroes() {
        mViewModel.heroesLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    mAdapter.setHeroes(state.data)
                    showLoading(false)
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        })

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getHeroes()
        }

        // If State isn't `Success` then reload posts.
        if (mViewModel.heroesLiveData.value !is State.Success) {
            getHeroes()
        }
    }

    private fun getHeroes() {
        mViewModel.getHeroes()
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            println("STATE CHANGED = $isConnected")
            if (!isConnected) {
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (mViewModel.heroesLiveData.value is State.Error || mAdapter.itemCount == 0) {
                    getHeroes()
                }
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        })
    }

    override fun onItemClicked(heroModel: MarvelHeroEntityModel) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DETAIL_NAME,heroModel.name)
        intent.putExtra(DetailsActivity.DETAIL_DESCRIPTION,heroModel.description)
        intent.putExtra(DetailsActivity.DETAIL_IMAGE, heroModel.photoUrl)
        startActivity(intent)
    }
}
