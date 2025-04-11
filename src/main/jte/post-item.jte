@import com.example.models.Post
@import com.example.models.User
@import java.time.format.DateTimeFormatter
@import java.time.LocalDateTime

<%-- Іменовані параметри з значеннями за замовчуванням (f + g) --%>
@param Post post
@param boolean showCategory = true
@param User currentUser = null
@param boolean showFullContent = false

<%-- Змінні (j) --%>
<%
var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
var now = LocalDateTime.now();
boolean isNew = post.getCreatedAt().isAfter(now.minusDays(1));
String timeAgo = getTimeAgo(post.getCreatedAt());
%>

<div class="post-item ${isNew ? "post-new" : ""}">
    <div class="post-header">
        <div class="post-title-section">
            <a href="/post/${post.getId()}" class="post-title">${post.getTitle()}</a>
            @if(isNew)
                <span class="post-badge new-badge">Нове</span>
            @endif
        </div>
        <span class="post-meta" title="${post.getCreatedAt().format(formatter)}">${timeAgo}</span>
    </div>

    @if(showFullContent)
        <div class="post-content">${post.getContent()}</div>
    @else
        <p class="post-excerpt">${post.getExcerpt()}</p>
    @endif

    <div class="post-footer">
        <div class="post-author">
            <img src="/images/avatars/${post.getAuthor().getAvatar()}" alt="${post.getAuthor().getUsername()}" class="avatar-small">
            <span class="post-meta">Автор: ${post.getAuthor().getUsername()}</span>
        </div>

        @if(showCategory)
            <a href="/category/${post.getCategory().getId()}" class="post-category">
                ${post.getCategory().getName()}
            </a>
        @endif

        <div class="tags">
            @for(var tag : post.getTags())
                <a href="/tag/${tag}" class="tag">${tag}</a>
            @endfor
        </div>

        <div class="post-stats">
            <span class="post-meta replies">${post.getReplyCount()} відповідей</span>
            <span class="post-meta views">${post.getViewCount()} переглядів</span>
        </div>
    </div>

    <%-- Відображення кнопок дій для автора поста (b) --%>
    @if(currentUser != null && post.getAuthor().getId() == currentUser.getId())
        <div class="post-actions">
            <a href="/post/${post.getId()}/edit" class="btn btn-sm">Редагувати</a>
            <a href="/post/${post.getId()}/delete" class="btn btn-sm btn-danger">Видалити</a>
        </div>
    @endif
</div>

<%!
// Допоміжний метод для обчислення "часу тому"
private static String getTimeAgo(LocalDateTime dateTime) {
LocalDateTime now = LocalDateTime.now();
long minutes = java.time.Duration.between(dateTime, now).toMinutes();

if (minutes < 1) return "Тільки що";
if (minutes < 60) return minutes + " хв. тому";

long hours = minutes / 60;
if (hours < 24) return hours + " год. тому";

long days = hours / 24;
if (days < 30) return days + " дн. тому";

long months = days / 30;
if (months < 12) return months + " міс. тому";

return days / 365 + " р. тому";
}
%>