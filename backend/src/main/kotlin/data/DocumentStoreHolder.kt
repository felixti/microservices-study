package data

import net.ravendb.client.documents.DocumentStore
import net.ravendb.client.documents.IDocumentStore
import java.io.FileInputStream

import java.security.KeyStore

object DocumentStoreHolder {
    private val store: DocumentStore

    init {
        val clientStore = KeyStore.getInstance("PKCS12").also {
            it.load(FileInputStream("./certs/free.felixtilabs.client.certificate.with.password.pfx"), "2288E96AB0CF7EFBABF328A14D93431E".toCharArray())
        }

        store = DocumentStore()
        store.certificate = clientStore
        store.database = "Movies"
        store.urls = arrayOf("https://a.free.felixtilabs.ravendb.cloud")
        store.initialize()
    }

    fun getStore(): IDocumentStore = store

}