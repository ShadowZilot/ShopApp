package com.egorponomarev.theme.base

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface ContentService<T> {

    suspend fun content() : T
}