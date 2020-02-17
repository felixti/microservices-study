package com.felix.labs.category.handlers

import com.felix.labs.category.data.CategoryRepository
import com.felix.labs.category.model.Category
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.web.servlet.function.ServerRequest

@RunWith(MockitoJUnitRunner::class)
class CategoryHandlerTests {

    @Mock
    lateinit var repository: CategoryRepository

    @Mock
    lateinit var serverRequest: ServerRequest

    @InjectMocks
    lateinit var handler: CategoryHandler

    @Before
    fun setUp(){}

    @Test
    fun `when called read all should return a list of categories`() {
        doReturn(mutableListOf<Category>().apply {
            addAll(listOf(Category("Comedy", "Comedy Description", true),
                    Category("Horror", "Horror Description", true),
                    Category("Romance", "Romance Description", false)))
        }).`when`(repository).findAll()
        val result = handler.readAll(serverRequest)
        assertNotNull(result)
        assert(result.statusCode().is2xxSuccessful)
    }

    @Test
    fun `given id parameter should return a category`() {
        val categoryMock = Category("Comedy", "Comedy Description", true)
       doReturn(categoryMock).`when`(repository).findOne("id_12345")
        `when`(serverRequest.pathVariable("id")).thenReturn("id_12345")
        val result = handler.readOne(serverRequest)
        assertNotNull(result)
        assert(result.statusCode().is2xxSuccessful)
    }

    @Test
    fun `given new category info should be created successfully`() {
        val categoryMock = Category("Category 1", "category 1 description", true)
        doReturn(categoryMock).`when`(repository).createOne(categoryMock)
        `when`(serverRequest.body(Category::class.java)).thenReturn(categoryMock)
        val result = handler.create(serverRequest)
        assertNotNull(result)
        assert(result.statusCode().is2xxSuccessful)
    }

    @Test
    fun `given id and modified category should update successfully`() {
        val id = "id_12345"
        val categoryMock = Category("Category 1 - updated", "category 1 description - updated", true)
        doReturn(categoryMock).`when`(repository).updateOne(id, categoryMock)
        `when`(serverRequest.pathVariable("id")).thenReturn(id)
        `when`(serverRequest.body(Category::class.java)).thenReturn(categoryMock)
        val result = handler.update(serverRequest)
        assertNotNull(result)
        assert(result.statusCode().is2xxSuccessful)
    }

    @Test
    fun `given id should delete category`() {
        val id = "id_12345"
        `when`(serverRequest.pathVariable("id")).thenReturn(id)
        val result = handler.delete(serverRequest)
        assertNotNull(result)
        assert(result.statusCode().is2xxSuccessful)
    }
}