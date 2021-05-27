package com.victormartin.ownpractice.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import com.victormartin.ownpractice.databinding.ActivityDetailsBinding
import com.victormartin.ownpractice.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : BaseActivity<DetailsViewModel, ActivityDetailsBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(this.mViewBinding.root)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.extras?.getString(DETAIL_NAME) ?: throw IllegalArgumentException("Name must be non-null")
        val description = intent.extras?.getString(DETAIL_DESCRIPTION) ?: throw IllegalArgumentException("Description must be non-null")
        val image = intent.extras?.getString(DETAIL_IMAGE) ?: throw IllegalArgumentException("Image must be non-null")

        initDetails(name, image)

    }


    private fun initDetails(name: String, image: String) {
        mViewModel.getHero(name).observe(this, Observer { heroModel ->
            mViewBinding.heroContent.apply {
                heroDetailName.text = heroModel.name
                heroDetailDescription.text = heroModel.description
                heroModel?.photoUrl?.let {
                    loadImage(heroDetailImage, heroModel.photoUrl)
                } ?: run {
                    loadImage(heroDetailImage, image)
                }
            }
        })

    }

    fun loadImage(imageView: ImageView, uri: String) {
        imageView.load(uri) {
            crossfade(true)
            placeholder(android.R.drawable.ic_popup_sync)
            transformations(CircleCropTransformation())
        }
    }

    companion object {
        const val DETAIL_NAME = "name"
        const val DETAIL_DESCRIPTION = "description"
        const val DETAIL_IMAGE = "photoUrl"
    }

    override fun getViewBinding(): ActivityDetailsBinding =
        ActivityDetailsBinding.inflate(layoutInflater)

    override val mViewModel: DetailsViewModel by viewModel()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}