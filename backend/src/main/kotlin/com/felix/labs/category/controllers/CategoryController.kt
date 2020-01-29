package com.felix.labs.category.controllers

import com.felix.labs.category.data.DocumentStoreHolder
import com.felix.labs.category.model.Category
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


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
    fun readOne(@PathVariable(value = "id") id: String): ResponseEntity<Category> {
        store.openSession().apply {
            val category = load(Category::class.java, id) ?: return ResponseEntity.notFound().build()
            return ResponseEntity.ok().body(category)
        }
    }

    @PostMapping("")
    fun createOne(@RequestBody category: Category): ResponseEntity<Category> {
        store.openSession().apply {
            store(category)
            saveChanges()
        }
        return ResponseEntity.created(URI("/api/categories/${category.id}")).body(category)
    }

    @PutMapping("/{id}")
    fun updateOne(@PathVariable(value = "id") id: String, @RequestBody updated: Category): ResponseEntity<Category> {
        store.openSession().apply {
            val original = (load(Category::class.java, id) ?: return ResponseEntity.notFound().build()).also {
                it.update(updated.name, updated.description, updated.active)
            }
            saveChanges()
            return ResponseEntity.ok().body(original)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable(value = "id") id: String): ResponseEntity.HeadersBuilder<*> {
        store.openSession().apply {
            delete(id)
            saveChanges()
            return ResponseEntity.noContent()
        }
    }
}