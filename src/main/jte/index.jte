@import com.shevchenko.mentalHealthSupport.models.Category
@import com.shevchenko.mentalHealthSupport.models.Post
@import java.util.List
@import java.time.format.DateTimeFormatter

<%-- Відображення даних (a) та іменовані параметри (f) --%>
@param List<Category> categories
@param List<Post> recentPosts
@param String userName = null <%-- Значення за замовчуванням (g) --%>

<%-- Коментар: Це головна сторінка форуму ментальної підтримки (d) --%>

<%-- Оголошення локальних змінних (j) --%>
<%
var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
var totalPosts = recentPosts.size();
%>

@template.layout(
title = "Головна сторінка",
description = "Форум ментальної підтримки - спільнота взаємодопомоги",
showHero = true,
content = @`

    @if(userName != null) <%-- If statement (b) --%>
        <div class="welcome-message">
            <h2>Вітаємо, ${userName}!</h2>
            <p>Раді бачити вас знову на нашому форумі.</p>
        </div>
    @endif

    <div class="intro">
        <h2>Наші категорії</h2>
        <p>На форумі наразі доступно ${categories.size()} категорій та ${totalPosts} публікацій.</p>
    </div>

    <div class="categories-wrapper">
        <div class="categories">
            <%-- Цикли (c) --%>
            @for(Category category : categories)
                <div class="category-card">
                    <img src="/images/${category.getImageUrl()}" alt="${category.getName()}">
                    <div class="category-content">
                        <h3>${category.getName()}</h3>
                        <p>${category.getDescription()}</p>
                        <a href="/category/${category.getId()}" class="btn">Переглянути тему</a>
                    </div>
                    <div class="category-footer">
                        <span class="category-meta">${category.getDiscussionCount()} обговорень</span>
                        <span class="category-meta">${category.getUserCount()} учасників</span>
                    </div>
                </div>
            @endfor
        </div>
    </div>

    <div class="recent-posts">
        <h2>Останні обговорення</h2>

        @if(recentPosts.isEmpty()) <%-- Ще один приклад if statement (b) --%>
            <p>Ще немає публікацій. Будьте першим, хто розпочне обговорення!</p>
        @else
        <%-- Цикли з умовою (b + c) --%>
            @for(var i = 0; i < recentPosts.size(); i++)
                @if(i < 5) <%-- Показуємо лише 5 останніх постів --%>
                    <% Post post = recentPosts.get(i); %>
                    <div class="post-item">
                        <div class="post-header">
                            <a href="/post/${post.getId()}" class="post-title">${post.getTitle()}</a>
                            <span class="post-meta">${post.getCreatedAt().format(formatter)}</span>
                        </div>
                        <p class="post-excerpt">${post.getExcerpt()}</p>
                        <div class="post-footer">
                            <span class="post-meta">Автор: ${post.getAuthor().getUsername()}</span>
                            <div class="tags">
                                <%-- Вкладений цикл для тегів (c) --%>
                                @for(String tag : post.getTags())
                                    <span>${tag}</span>
                                @endfor
                            </div>
                            <span class="post-meta">${post.getReplyCount()} відповідей</span>
                        </div>
                    </div>
                @endif
            @endfor
        @endif
    </div>
`
)