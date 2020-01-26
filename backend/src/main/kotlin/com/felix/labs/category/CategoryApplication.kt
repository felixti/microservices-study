package com.felix.labs.category

import data.DocumentStoreHolder
import model.Category
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@SpringBootApplication
class CategoryApplication

fun main(args: Array<String>) {
    runApplication<CategoryApplication>(*args)
}

@RestController
@RequestMapping("/api/categories")
class CategoryController {
    private var store = DocumentStoreHolder.store

    @GetMapping("")
    fun readAll(): MutableList<Category>? {
        store.openSession().also {
            return it.advanced()
                    .documentQuery(Category::class.java)
                    .toList()
        }
    }

    @GetMapping("/{id}")
    fun readOne(@PathVariable(value = "id") id: String): Category? {
        store.openSession().also {
            return it.load(Category::class.java, id)
        }
    }
}