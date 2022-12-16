package com.example.android.sports.model

import com.example.android.sports.R

/**
 * Model data untuk setiap baris RecyclerView
 */
data class Sport(
    val id: Int,
    val titleResourceId: Int,
    val subTitleResourceId: Int,
    val imageResourceId: Int,
    val sportsImageBanner: Int,
    val newsDetails: Int = R.string.sports_news_detail_text
)

