package com.muhardin.endy.belajar.htmx.service;

import com.muhardin.endy.belajar.htmx.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final TodoService todoService;

    public SearchService(TodoService todoService) {
        this.todoService = todoService;
    }

    public List<SearchResult> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        String searchTerm = query.trim().toLowerCase();
        List<SearchResult> results = new ArrayList<>();

        // Search dalam todos
        for (Todo todo : todoService.findAll()) {
            if (todo.getTitle().toLowerCase().contains(searchTerm)) {
                results.add(new SearchResult(
                    "todo",
                    todo.getTitle(),
                    highlightMatch(todo.getTitle(), searchTerm),
                    "/lesson/4",
                    todo.isCompleted() ? "Selesai" : "Belum selesai"
                ));
            }
        }

        return results;
    }

    private String highlightMatch(String text, String searchTerm) {
        if (text == null || searchTerm == null) {
            return text;
        }

        // Case-insensitive replace dengan highlight
        String lowerText = text.toLowerCase();
        String lowerSearch = searchTerm.toLowerCase();

        int index = lowerText.indexOf(lowerSearch);
        if (index == -1) {
            return text;
        }

        StringBuilder result = new StringBuilder();
        int lastIndex = 0;

        while (index != -1) {
            result.append(text, lastIndex, index);
            result.append("<mark class=\"bg-yellow-200 text-gray-900 rounded px-1\">");
            result.append(text, index, index + searchTerm.length());
            result.append("</mark>");

            lastIndex = index + searchTerm.length();
            index = lowerText.indexOf(lowerSearch, lastIndex);
        }

        result.append(text.substring(lastIndex));
        return result.toString();
    }

    public static class SearchResult {
        private String type;
        private String title;
        private String highlightedTitle;
        private String url;
        private String badge;

        public SearchResult(String type, String title, String highlightedTitle, String url, String badge) {
            this.type = type;
            this.title = title;
            this.highlightedTitle = highlightedTitle;
            this.url = url;
            this.badge = badge;
        }

        public String getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getHighlightedTitle() {
            return highlightedTitle;
        }

        public String getUrl() {
            return url;
        }

        public String getBadge() {
            return badge;
        }
    }
}
