package com.example.yurtok.data.local

import android.content.Context
import javax.inject.Inject

class SearchHistory @Inject constructor(
    private val context: Context
) {
    private val prefs by lazy {
        context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    }

    fun load(): List<String> {
        val history = prefs.getStringSet("history", mutableSetOf())?.toList() ?: emptyList()
        return history.takeLast(10).reversed() // Последние 10 записей, новые сверху
    }

    fun add(query: String) {
        val history = LinkedHashSet(
            prefs.getStringSet("history", LinkedHashSet()) ?: LinkedHashSet()
        )

        // Удаляем старый запрос, если есть
        history.remove(query)
        // Добавляем новый в конец
        history.add(query)

        // Преобразуем в List и берем последние 10 элементов
        val trimmed = history.toList().takeLast(10)

        // Сохраняем обратно как LinkedHashSet (чтобы сохранить порядок)
        prefs.edit().putStringSet("history", LinkedHashSet(trimmed)).apply()
    }

    fun clear() {
        prefs.edit().remove("history").apply()
    }
}