package com.example.todo.model

class Todo(
        var title: String,
        var priority: Priority,
        var dueDate: String,
        var description: String
) {

    enum class Priority {
        LOW, MEDIUM, HIGH
    }

}