package com.andreich.navigation

object EventBus {
    private val subscribers = mutableMapOf<Class<*>, MutableList<(Any) -> Unit>>()

    fun <T> subscribe(eventClass: Class<T>, action: (T) -> Unit) {
        subscribers.getOrPut(eventClass) { mutableListOf() }.add(action as (Any) -> Unit)
    }

    fun <T> unsubscribe(eventClass: Class<T>, action: (T) -> Unit) {
        subscribers[eventClass]?.remove(action as (Any) -> Unit)
    }

    fun publish(event: Any) {
        subscribers[event::class.java]?.forEach { it(event) }
    }
}