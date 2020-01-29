package com.felix.labs.category.data

import net.ravendb.client.documents.DocumentStore

object DocumentStoreHolder {

    var store: DocumentStore = DocumentStore().apply {
        database = "Movies"
        urls = arrayOf("http://raspberrypi.local:8080")
        initialize()
    }
        private set
}