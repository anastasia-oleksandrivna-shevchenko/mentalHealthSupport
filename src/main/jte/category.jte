@import com.example.models.Category
@import com.example.models.Post
@import com.example.models.User
@import java.util.List
@import java.time.LocalDateTime

<%-- Іменовані параметри (f) --%>
@param Category category
@param List<Post> posts
@param User currentUser = null <%-- Значення за замовчуванням (g) --%>
@param boolean isSubscribed = false

<%-- Виклик шаблону (e) з переданням контенту (i) --%>
@template.layout(
title = category.getName(),
description = category.getDescription(),
showHero = false,
content = @`
<%-- Використання локальних змінних (j) --%>
    <%
    var postCount = posts.size();
    var now = LocalDateTime.now();
    %>

    <div class="category-header">
        <div class="category-info">
            <h1>${category.getName()}</h1>
            <p>${category.getDescription()}</p>

            <%-- If statement з else (b) --%>
            @if(currentUser != null)
                @if(isSubscribed)
                    <button class="btn btn-secondary">Відписатися</button>
                @else
                    <button class="btn btn-primary">Підписатися</button>
                @endif
                <a href="/category/${category.getId()}/new-post" class="btn btn-success">Створити пост</a>
            @else
                <a href="/login" class="btn">Увійдіть, щоб створити пост</a>
            @endif
        </div>

        <div class="category-stats">
            <div class="stat">
                <strong>${category.getDiscussionCount()}</strong>
                <span>Обговорень</span>
            </div>
            <div class="stat">
                <strong>${category.getUserCount()}</strong>
                <span>Учасників</span>
            </div>
            <div class="stat">
                <strong>${category.getViewCount()}</strong>
                <span>Переглядів</span>
            </div>
        </div>
    </div>

    <%-- Підключення часткового шаблону (e) з параметрами --%>
    @template.partials.filter-bar(
    categoryId = category.getId(),
    sortOptions = new String[]{"newest", "popular", "unanswered"}
    )

    <div class="post-list">
        <%-- Цикл з перевіркою на пустоту (b + c) --%>
        @if(posts.isEmpty())
            <div class="empty-state">
                <h3>У цій категорії ще немає постів</h3>
                <p>Будьте першим, хто створить обговорення!</p>
                @if(currentUser != null)
                    <a href="/category/${category.getId()}/new-post" class="btn btn-primary">Створити перший пост</a>
                @endif
            </div>
        @else
            @for(Post post : posts)
            <%-- Вставка часткового шаблону (e) --%>
                @template.partials.post-item(
                post = post,
                showCategory = false,
                currentUser = currentUser
                )
            @endfor

            <%-- Виклик шаблону пагінації з varargs параметрами (h) --%>
            @template.partials.pagination(
            currentPage = 1,
            totalPages = (int)Math.ceil(postCount / 10.0),
            baseUrl = "/category/" + category.getId()
            )
        @endif
    </div>

    <%-- Використання if з елементами порівняння дат (b + j) --%>
    @if(category.getLastUpdated() != null && category.getLastUpdated().isAfter(now.minusDays(1)))
        <div class="notification">
            <p>Ця категорія була оновлена менше 24 годин тому.</p>
        </div>
    @endif
`
)