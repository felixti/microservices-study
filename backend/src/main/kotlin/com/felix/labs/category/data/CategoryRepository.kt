package com.felix.labs.category.data

import com.felix.labs.category.model.Category
import org.springframework.stereotype.Component

@Component
class CategoryRepository {
    private var store = DocumentStoreHolder.store

    fun findAll(): MutableList<Category>? {
        store.openSession().also {
            return it.advanced()
                    .documentQuery(Category::class.java)
                    .toList()
        }
    }

    fun findOne(id: String): Category? {
        store.openSession().apply { return load(Category::class.java, id) }
    }

    fun createOne(category: Category): Category {
        store.openSession().apply {
            store(category)
            saveChanges()
        }
        return category
    }

    fun updateOne(id: String, updated: Category): Category? {
        store.openSession().apply {
            val original = (load(Category::class.java, id) ?: return null).also {
                it.update(updated.name, updated.description, updated.active)
            }
            saveChanges()
            return original
        }
    }

    fun deleteOne(id: String) {
        store.openSession().apply {
            delete(id)
            saveChanges()
        }
    }
}