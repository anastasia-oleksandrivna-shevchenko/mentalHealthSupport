@param java.util.List<com.shevchenko.mentalHealthSupport.models.Category> categories

@template.layout(
title = "Катагорії",
description = "Наявні категорії на форумі",
showHero = false,
content = @`
    <div class="intro">
        <h2>Наші категорії</h2>
        <p>На форумі наразі доступно ${categories.size()} категорій</p>
    </div>
    <div class="categories-wrapper">
        <div class="categories">
            @for(com.shevchenko.mentalHealthSupport.models.Category category : categories)
                <div class="category-card">
                    <div class="category-layout">
                        <div class="category-image">
                            <img src="${category.getImageUrl()}" alt="${category.getName()}">
                        </div>
                        <div class="category-content">
                            <h3>${category.getName()}</h3>
                            <p>${category.getDescription()}</p>
                            <a href="/category/${category.getName()}" class="btn">Переглянути тему</a>
                        </div>
                    </div>
                    <div class="category-footer">
                        <span class="category-meta">${category.getDiscussionCount()} обговорень</span>
                        <span class="category-meta">${category.getUserCount()} учасників</span>
                    </div>
                </div>
            @endfor
        </div>
    </div>
`
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