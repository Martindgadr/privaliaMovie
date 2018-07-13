package com.ionnt.privalia.utils

/**
 * Created by Martin De Girolamo on 15/06/2018.
 */
class Constants {
    companion object {
        // Header Request
        const val CONTENT_TYPE = "application/json"
        const val TRAKT_API_VERSION = "2"

        // URL Service Composition.
        const val BASE_URL = "https://api.trakt.tv/"
        const val BASE_FANART_URL = "http://webservice.fanart.tv/v3/movies/"
        const val MOST_POPULAR_REQUEST = "movies/popular/"
        const val MOVIE_SEARCH_REQUEST = "search/movie/"

        // Tokens and public keys
        const val TRAKT_API_KEY = "019a13b1881ae971f91295efc7fdecfa48b32c2a69fe6dd03180ff59289452b8"
        const val FANART_API_KEY = "0aeebe1b152ef0522efe3ede05b3448e"
        const val ACCESS_TOKEN = "4131eed08a1a1efedad9a42322072b2115fdffaa4e014cbaa8a02f76710f3dfb"
        const val REFRESH_TOKEN = "f33b0fe23778b5247a7379b61ecc10b722ec107e8e1e92223f6693344a05d13a"

        // QueryMap Fields
        const val API_KEY_FIELD = "api_key"
        const val CLIENT_KEY_FIELD = "client_key"
        const val EXTENDED_INFO_TYPE = "full"
        const val QUERY_FIELD = "query"
        const val EXTENDED_FIELD = "extended"
        const val PAGE_FIELD = "page"
    }
}