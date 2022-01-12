package com.example.android.architecture.blueprints.todoapp

import android.app.Application
import com.frogobox.nutritioncore.method.function.ConsumeNewsApi
import com.frogobox.nutritioncore.model.news.Article
import com.frogobox.nutritioncore.model.news.ArticleResponse
import com.frogobox.nutritioncore.sources.NutriResponse
import com.frogobox.nutritioncore.util.news.NewsConstant
import com.frogobox.nutritionframework.core.NutriViewModel
import com.frogobox.nutritionframework.util.NutriSingleLiveEvent


/*
 * Created by faisalamir on 10/01/22
 * nf-testcase-googlesample-consume-library
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * Copyright (C) 2022 FrogoBox Inc.      
 * All rights reserved
 *
 */

class MainViewModel(
    private val context: Application,
    private val newsApi: ConsumeNewsApi
) :
    NutriViewModel(context) {

    val listData = NutriSingleLiveEvent<List<Article>>()

    fun getData(query: String) {
        newsApi.getEverythings(
            query,
            null,
            null,
            null,
            null,
            null,
            null,
            NewsConstant.COUNTRY_ID,
            null,
            null,
            null,
            object : NutriResponse.DataResponse<ArticleResponse> {
                override fun onEmpty() {
                    eventEmptyData.postValue(true)
                }

                override fun onFailed(statusCode: Int, errorMessage: String?) {
                    eventFailed.postValue(errorMessage)
                }

                override fun onHideProgress() {
                    eventShowProgress.postValue(false)
                }

                override fun onShowProgress() {
                    eventShowProgress.postValue(true)
                }

                override fun onSuccess(data: ArticleResponse) {
                    listData.postValue(data.articles!!)
                }
            }
        )
    }


}