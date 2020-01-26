package data

import net.ravendb.client.documents.DocumentStore
import net.ravendb.client.documents.IDocumentStore
import java.io.FileInputStream

import java.security.KeyStore

//const val RAVENDB_PASSWORD: String = "2288E96AB0CF7EFBABF328A14D93431E"
//const val RAVENDB_CERTFILE = "c:\\Users\\USUARIO\\source\\repos\\MicroservicesCourse\\laravel-app\\laravel\\backend\\certs\\ravendb.pfx"

object DocumentStoreHolder {
    var store: DocumentStore = DocumentStore().apply {
        database = "Movies"
        urls = arrayOf("http://raspberrypi.local:8080")
        initialize()
    }
        private set

    //    init {
//        val clientStore = KeyStore.getInstance("PKCS12")
//        clientStore.load(FileInputStream(RAVENDB_CERTFILE), RAVENDB_PASSWORD.toCharArray())
//
//        store = DocumentStore().also {
//            it.certificate = clientStore
//            it.database = "Movies"
//            it.urls = arrayOf("https://a.free.felixtilabs.ravendb.cloud")
//            it.initialize()
//        }
//    }


}