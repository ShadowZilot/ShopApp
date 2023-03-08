package com.egorponomarev.store.data

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface ContentService<T> {

    suspend fun content() : T
}