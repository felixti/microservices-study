package model

class Category {

    constructor() {
    }

    constructor(name: String, description: String, active: Boolean) {
        this.name = name
        this.description = description
        this.active = active
    }

    private constructor(id: String, name: String, description: String, active: Boolean?) : this(name, description, active!!) {
        this.id = id
    }

    var id: String = ""
        private set

    var name: String = ""
        private set

    var description: String = ""
        private set

    var active: Boolean? = null
        private set

    fun update(name: String, description: String, active: Boolean?): Category =
            Category(this.id, name, description, active)
}