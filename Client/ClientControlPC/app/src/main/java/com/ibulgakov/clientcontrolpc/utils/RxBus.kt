package com.ibulgakov.clientcontrolpc.utils

import rx.Observable
import rx.Subscriber
import rx.subjects.PublishSubject
import rx.subjects.Subject

class RxBus<T> @JvmOverloads constructor(private val subject: Subject<T, T> = PublishSubject.create<T>().toSerialized()) {

    fun <E : T> send(event: E) {
        subject.onNext(event)
    }

    fun observeEvents(): Observable<T> {
        return subject
    }

    fun <E : T> observeEvents(eventClass: Class<E>): Observable<E> {
        return subject.ofType(eventClass)
    }
}

abstract class AbstractSubscriber<T> : Subscriber<T>() {
    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
    }
}