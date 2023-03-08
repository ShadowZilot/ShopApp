package com.egorponomarev.theme.base

import okhttp3.OkHttpClient

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
object GlobalClient {
    private val mClient = OkHttpClient()

    operator fun invoke() = mClient
}