package com.felix.labs.category.data

import net.ravendb.client.documents.DocumentStore

object DocumentStoreHolder {

    var store: DocumentStore = DocumentStore().apply {
        database = "Movies"
        urls = arrayOf("http://134.209.174.160:8080")
        initialize()
    }
        private set
}