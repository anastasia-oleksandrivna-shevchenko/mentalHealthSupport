@import com.shevchenko.mentalHealthSupport.models.Category

@param java.util.List<Category> categories
@param boolean isAdmin
@param boolean isLoggedIn


@template.layout(
title = "Катагорії",
description = "Наявні категорії на форумі",
showHero = false,
content = @`
    <div class="intro">
        <h2>Наші категорії</h2>
        <p>На форумі наразі доступно ${categories.size()} категорій</p>
        @if(isAdmin)
            <a href="/category/new" class="add-button">+ Додати категорію</a>
        @endif
    </div>
    <div class="categories-wrapper">
        <div class="categories">
            @for(Category category : categories)
                <div class="category-card">
                    <div class="category-layout">
                        <div class="category-image">
                            <img src="${category.getImageUrl()}" alt="${category.getCategoryid()}">
                        </div>
                        <div class="category-content">
                            <h3>${category.getName()}</h3>
                            <p>${category.getDescription()}</p>
                            <a href="/category/${category.getCategoryid()}" class="btn">Переглянути тему</a>
                        </div>
                    </div>
                    <div class="category-footer">
                        <span class="category-meta">${category.getDiscussionCount()} обговорень</span>
                    </div>
                    @if(isAdmin)
                        <form method="post" action="/category/${category.getCategoryid()}/delete" class="delete-form">
                            <button type="submit" class="delete-button">Видалити</button>
                        </form>
                    @endif
                </div>
            @endfor
        </div>
    </div>
`,
isLoggedIn = isLoggedIn
)

<style>
    .category-card {
        border: 1px solid #e0e0e0;
        border-radius: 8px;
        margin-bottom: 20px;
        overflow: hidden;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .category-layout {
        display: flex;
        align-items: flex-start;
    }

    .category-image {
        width: 150px;
        min-width: 150px;
        padding: 15px;
    }

    .category-image img {
        width: 100%;
        height: auto;
        border-radius: 6px;
        object-fit: cover;
    }

    .category-content {
        padding: 15px;
        flex-grow: 1;
    }

    .category-content h3 {
        margin-top: 0;
        margin-bottom: 10px;
    }

    .category-footer {
        background-color: #f5f5f5;
        padding: 10px 15px;
        display: flex;
        justify-content: space-between;
    }

    .btn {
        display: inline-block;
        padding: 8px 15px;
        background-color: #3498db;
        color: white;
        text-decoration: none;
        border-radius: 4px;
        margin-top: 10px;
    }
</style>