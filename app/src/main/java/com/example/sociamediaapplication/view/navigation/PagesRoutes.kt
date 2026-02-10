package com.example.sociamediaapplication.view.navigation

sealed class PagesRoutes(val route: String) {
    object Pages: PagesRoutes("pages")
    object Page: PagesRoutes("page/{pageId}"){
        fun createRoute(pageId: String) = "page/$pageId"
    }
    object CreatePage: PagesRoutes("createPage")
}