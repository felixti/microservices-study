package com.felix.labs.category

import data.DocumentStoreHolder
import model.Category
import net.ravendb.client.exceptions.RavenException
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class CategoryApplication

fun main(args: Array<String>) {
    runApplication<CategoryApplication>(*args)
}

@RestController
@RequestMapping("/api/category")
class CategoryController {
    private var store = DocumentStoreHolder.getStore()

    @GetMapping("/")
    fun readAll(): MutableList<Category>? {
        try {
            store.openSession().also {

                it.store(Category("Felipe Augusto Felix", "Teste", true))
                it.saveChanges()

                return it.advanced()
                        .documentQuery(Category::class.java)
                        .toList()
            }
        } catch (ex: RavenException) {
            throw ex
        }
    }
}