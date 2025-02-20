package com.example.testwithpoetry.presentation.navigation

sealed class Route(val route: String, val title: String) {
    data object Welcome : Route(route = "welcome_route", title = "Welcome")
    data object Authors : Route(route = "authors_route", title = "Authors")
    data object Poetry : Route(route = "poetry_route", title = "Poetry")
    data object Account : Route(route = "account_route", title = "Account")
    data object AuthorsList : Route(route = "authors_list_route", title = "Authors List")
    data object AuthorDetails : Route(
        route = "author_details_route/{author}",
        title = "Author Details"
    ) {
        fun createRoute(author: String) = "author_details_route/$author"
    }
    data object Profile : Route(route = "profile_route", title = "Profile")
}