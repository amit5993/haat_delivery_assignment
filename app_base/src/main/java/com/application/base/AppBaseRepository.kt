package com.application.base

// T => Template types
abstract class AppBaseRepository<T : Any>(apiClass: T) {
    var api: T = apiClass
}