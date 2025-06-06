package com.example.yurtok.presentation.navigation

import com.example.yurtok.R

sealed class BottonNavItem(val route: String,
                           val title: String,
                           val icon: Int, val iconFocused: Int)
{
    object Search: BottonNavItem(
        route = Route.SEARCH,
        title = "Search",
        icon = R.drawable.search,
        iconFocused = R.drawable.search2
    )
    object Favorites: BottonNavItem(
        route = Route.FAVOURITES,
        title = "Favorites",
        icon = R.drawable.bookmark,
        iconFocused = R.drawable.bookmark2
    )
    object Profile: BottonNavItem(
        route = Route.PROFILE,
        title = "Profile",
        icon = R.drawable.profile,
        iconFocused = R.drawable.profile2
    )
    object Application: BottonNavItem(
        route =Route.APPLICATION,
        title = "Application",
        icon = R.drawable.application,
        iconFocused = R.drawable.application_filled
    )
}