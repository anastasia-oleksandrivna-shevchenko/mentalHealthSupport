@import com.example.models.Post
@import com.example.models.Comment
@import com.example.models.User
@import java.util.List
@import java.time.format.DateTimeFormatter

<%-- Іменовані параметри (f) --%>
@param Post post
@param List<Comment> comments
@param User currentUser = null <%-- Default parameter (g) --%>
@param String errorMessage = null

<%-- Змінні (j) --%>
<%
var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
boolean canComment = currentUser != null;
boolean isAuthor = currentUser != null && post.getAuthor().getId() == currentUser.getId();
boolean isModerator = currentUser != null && currentUser.hasRole("MODERATOR");
%>

@template.layout(
title = post.getTitle(),
description = post.getExcerpt(),
showHero = false,
content = @`
    <div class="breadcrumbs">
        <a href="/">Головна</a> &gt;
        <a href="/category/${post.getCategory().getId()}">${post.getCategory().getName()}</a> &gt;
        <span>${post.getTitle()}</span>
    </div>

    <article class="post-full">
        <header class="post-header">
            <h1>${post.getTitle()}</h1>
            <div class="post-meta-info">
                <div class="author-info">
                    <img src="/images/avatars/${post.getAuthor().getAvatar()}" alt="${post.getAuthor().getUsername()}" class="avatar">
                    <div class="author-details">
                        <span class="author-name">${post.getAuthor().getUsername()}</span>
                        <span class="post-date" title="${post.getCreatedAt().format(formatter)}">
                            Опубліковано: ${post.getCreatedAt().format(formatter)}
                        </span>
                    </div>
                </div>

                @if(isAuthor || isModerator) <%-- If statement з логічним оператором OR (b) --%>
                    <div class="post-actions">
                        @if(isAuthor)
                            <a href="/post/${post.getId()}/edit" class="btn btn-sm">Редагувати</a>
                        @endif

                        @if(isModerator)
                            <a href="/post/${post.getId()}/moderate" class="btn btn-sm btn-warning">Модерація</a>
                        @endif

                        @if(isAuthor || isModerator)
                            <a href="/post/${post.getId()}/delete" class="btn btn-sm btn-danger">Видалити</a>
                        @endif
                    </div>
                @endif
            </div>
        </header>

        <div class="post-content">
            ${post.getContent()}
        </div>

        <footer class="post-footer">
            <div class="tags">
                @for(var tag : post.getTags())
                    <a href="/tag/${tag}" class="tag">${tag}</a>
                @endfor
            </div>

            <div class="post-reactions">
                <button class="reaction-btn" data-reaction="like">
                    <i class="icon-thumbs-up"></i>
                    <span>${post.getLikeCount()}</span>
                </button>
                <button class="reaction-btn" data-reaction="helpful">
                    <i class="icon-heart"></i>
                    <span>${post.getHelpfulCount()}</span>
                </button>
                <button class="reaction-btn" data-reaction="bookmark">
                    <i class="icon-bookmark"></i>
                    <span>${post.getBookmarkCount()}</span>
                </button>
            </div>
        </footer>
    </article>

    <section class="comments-section">
        <h2>Коментарі (${comments.size()})</h2>

        @if(errorMessage != null) <%-- Відображення помилки, якщо вона є (b) --%>
            <div class="alert alert-danger">
                ${errorMessage}
            </div>
        @endif

        @if(comments.isEmpty())
            <p class="no-comments">Поки що немає коментарів. Будьте першим, хто прокоментує!</p>
        @else
        <%-- Виклик часткового шаблону з varargs параметром (e + h) --%>
            @template.partials.comments(
            comments = comments,
            currentUser = currentUser,
            postId = post.getId(),
            highlightedCommentId = request.getParameter("highlight")
            )
        @endif

        @if(canComment)
            <div class="comment-form">
                <h3>Додати коментар</h3>
                <form action="/post/${post.getId()}/comment" method="post">
                    <div class="form-group">
                        <label for="comment">Ваш коментар:</label>
                        <textarea id="comment" name="content" rows="4" required></textarea>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Відправити</button>
                    </div>
                </form>
            </div>
        @else
            <div class="login-prompt">
                <p>Щоб залишити коментар, <a href="/login?redirect=/post/${post.getId()}">увійдіть</a> або <a href="/register">зареєструйтесь</a>.</p>
            </div>
        @endif
    </section>

    <%-- Відображення рекомендованих постів (e) --%>
    @if(!post.getRelatedPosts().isEmpty())
        <section class="related-posts">
            <h2>Схожі обговорення</h2>
            <div class="post-grid">
                <%-- Використання varargs для передачі кількох параметрів (h) --%>
                @template.partials.related-posts(
                posts = post.getRelatedPosts(),
                limit = 3,
                excludeId = post.getId()
                )
            </div>
        </section>
    @endif
`
)