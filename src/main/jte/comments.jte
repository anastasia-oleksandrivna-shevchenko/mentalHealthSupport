@import com.shevchenko.mentalHealthSupport.models.Post
@import com.shevchenko.mentalHealthSupport.models.Comment
@import java.time.format.DateTimeFormatter
@import java.util.List

@param List<Comment> comments
@param Long post
@param boolean isAdmin
@param boolean isLoggedIn

@template.layout(
title = "${post}",
description = "Усі комментарії поста",
showHero = false,
content = @`
    <div class="category-posts">

        <h2>Коментарі</h2>
        <a href="/post/${post}/new" class="add-button">+ Додати коментар</a>

        @if(comments.isEmpty())
            <p class="no-posts">Ще немає комментарів. Будьте першим, хто розпочне обговорення!</p>
        @else
            <div class="post-list">
                @for(Comment comment : comments)
                    <div class="post-card">
                        <div class="post-header">
                            <a href="/post/${comment.getId()}" class="post-title"></a>
                            <span class="post-date">${comment.getFormattedCreatedAt()}</span>
                        </div>
                        <p class="post-excerpt">${comment.getContent()}</p>
                        <div class="post-footer">
                            <div class="post-author">
                                @if(!comment.getIs_anonymous())
                                <span>Автор: ${comment.getUser().getUsername()}</span>
                                @endif
                            </div>
                            <div class="post-stats">
                                <span class="reply-count">0 відповідей</span>
                            </div>
                            @if(isAdmin)
                                <form method="post" action="/comment/${comment.getId()}/delete" class="delete-form">
                                    <button type="submit" class="delete-button">Видалити</button>
                                </form>
                            @endif
                        </div>
                    </div>
                @endfor
            </div>
        @endif
    </div>
`,
isLoggedIn = isLoggedIn
)
<style>
    .category-posts {
        margin-top: 40px;
        padding: 0 10px;
    }

    .pcategory-posts h2 {
        margin-bottom: 20px;
        font-size: 24px;
        color: #333;
        border-bottom: 2px solid #3498db;
        padding-bottom: 10px;
    }

    .no-posts {
        text-align: center;
        padding: 30px;
        background: #f9f9f9;
        border-radius: 8px;
        color: #7f8c8d;
        font-style: italic;
    }

    .post-list {
        display: grid;
        gap: 20px;
    }

    .post-card {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .post-card:hover {
        transform: translateY(-3px);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
    }

    .post-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 12px;
    }

    .post-title {
        font-size: 18px;
        font-weight: 600;
        color: #2c3e50;
        text-decoration: none;
        transition: color 0.2s ease;
    }

    .post-title:hover {
        color: #3498db;
    }

    .post-date {
        font-size: 14px;
        color: #7f8c8d;
    }

    .post-excerpt {
        color: #34495e;
        margin-bottom: 15px;
        line-height: 1.5;
    }

    .post-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-top: 12px;
        border-top: 1px solid #ecf0f1;
    }

    .post-author {
        display: flex;
        align-items: center;
        gap: 5px;
        color: #7f8c8d;
        font-size: 14px;
    }

    .post-stats {
        font-size: 14px;
        color: #7f8c8d;
    }

    .reply-count {
        background-color: #e8f4fc;
        padding: 3px 8px;
        border-radius: 30px;
        color: #3498db;
        font-weight: 500;
    }

    .post-tags {
        display: flex;
        flex-wrap: wrap;
        gap: 6px;
        margin-top: 12px;
    }

    .tag {
        background-color: #f0f4f7;
        color: #5d6d7e;
        padding: 3px 10px;
        border-radius: 15px;
        font-size: 12px;
    }

    /* Додавання респонсивності */
    @media (min-width: 768px) {
        .post-list {
            grid-template-columns: repeat(2, 1fr);
        }
    }

    @media (min-width: 1200px) {
        .post-list {
            grid-template-columns: repeat(3, 1fr);
        }
    }
</style>