package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.architecture.blueprints.todoapp.databinding.ActivityMainBinding
import com.frogobox.nutritioncore.model.news.Article
import com.frogobox.nutritionframework.core.NutriActivity
import com.frogobox.nutritionframework.databinding.NutriRvListType11Binding
import com.frogobox.nutritionframework.recycler.core.INutriBuilderRvBinding
import com.frogobox.nutritionframework.recycler.core.NutriLayoutManager
import com.frogobox.nutritionframework.recycler.core.NutriRecyclerNotifyListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NutriActivity<ActivityMainBinding>() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        mainViewModel.apply {
            getData()

            listData.observe(this@MainActivity, {
                setupRv(it)
            })

            eventShowProgress.observe(this@MainActivity, {
                binding.progressBar.visibility =
                    if (it) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
    }

    private fun setupRv(data: List<Article>) {
        binding.rvMain.builderBinding(object :
            INutriBuilderRvBinding<Article, NutriRvListType11Binding> {

            override fun setViewBinding(parent: ViewGroup): NutriRvListType11Binding {
                return NutriRvListType11Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            override fun setupData(): List<Article> {
                return data
            }

            override fun setupLayoutManager(context: Context): RecyclerView.LayoutManager {
                return NutriLayoutManager.linearLayoutVertical(context)
            }

            override fun setupInitComponent(
                binding: NutriRvListType11Binding,
                data: Article,
                position: Int,
                listener: NutriRecyclerNotifyListener<Article>
            ) {
                binding.apply {
                    nutriRvListType11TvTitle.text = data.title
                    nutriRvListType11TvSubtitle.text = data.author
                    nutriRvListType11TvDesc.text = data.description
                    Glide.with(root.context).load(data.urlToImage).into(nutriRvListType11IvPoster)
                }
            }

            override fun onItemClicked(
                binding: NutriRvListType11Binding,
                data: Article,
                position: Int,
                listener: NutriRecyclerNotifyListener<Article>
            ) {
                baseStartActivity<DetailActivity, Article>(DetailActivity.EXTRA_DATA, data)
            }

            override fun onItemLongClicked(
                binding: NutriRvListType11Binding,
                data: Article,
                position: Int,
                listener: NutriRecyclerNotifyListener<Article>
            ) {
            }

        })

    }

}