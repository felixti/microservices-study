package com.felix.labs.category

import data.DocumentStoreHolder
import model.Category
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import java.net.URI

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

    @PostMapping("")
    fun createOne(@RequestBody category: Category): ResponseEntity<Category> {
        store.openSession().apply {
            store(category)
            saveChanges()
        }

        return created(URI("/api/categories/${category.id}")).body(category)
    }

    @PutMapping("/{id}")
    fun updateOne(@PathVariable(value = "id") id: String, @RequestBody category: Category): ResponseEntity<Category> {
        store.openSession().apply {
            val original = load(Category::class.java, id)
            val updated = original.update(category.name, category.description, category.active)
            store(updated)
            saveChanges()

            return ok().body(updated)
        }
    }
}