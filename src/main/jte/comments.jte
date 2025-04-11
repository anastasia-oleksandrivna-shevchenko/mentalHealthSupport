@import com.example.models.Comment
@import com.example.models.User
@import java.util.List
@import java.time.format.DateTimeFormatter

<%-- Іменовані параметри (f) + varargs (h) --%>
@param List<Comment> comments
@param User currentUser = null
@param Long postId
@param String... highlightedCommentId

<%-- Змінні (j) --%>
<%
var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
String highlighted = highlightedCommentId.length > 0 ? highlightedCommentId[0] : null;
%>

<div class="comments-list">
    <%-- Рекурсивний обхід коментарів з використанням цикла (c) --%>
    @for(Comment comment : comments)
    <%
    boolean isHighlighted = highlighted != null && comment.getId().toString().equals(highlighted);
    boolean isAuthor = currentUser != null && comment.getAuthor().getId() == currentUser.getId();
    boolean canReply = currentUser != null;
    %>

    <div id="comment-${comment.getId()}" class="comment ${isHighlighted ? "highlighted" : ""}">
        <div class="comment-header">
            <div class="comment-author">
                <img src="/images/avatars/${comment.getAuthor().getAvatar()}" alt="${comment.getAuthor().getUsername()}" class="avatar-small">
                <div class="author-info">
                    <span class="author-name">${comment.getAuthor().getUsername()}</span>
                    <span class="comment-date" title="${comment.getCreatedAt().format(formatter)}">
                            ${comment.getCreatedAt().format(formatter)}
                        </span>
                </div>
            </div>

            @if(isAuthor && !comment.isEdited() && (System.currentTimeMillis() - comment.getCreatedAt().toEpochMilli()) < 3600000)
                <div class="comment-actions">
                    <a href="/comment/${comment.getId()}/edit" class="btn-link">Редагувати</a>
                    <a href="/comment/${comment.getId()}/delete" class="btn-link text-danger">Видалити</a>
                </div>
            @endif
        </div>

        <div class="comment-content">
            ${comment.getContent()}
            @if(comment.isEdited())
                <span class="edited-mark">(відредаговано)</span>
            @endif
        </div>

        <div class="comment-footer">
            <div class="comment-reactions">
                <button class="reaction-btn-sm" data-reaction="like" data-id="${comment.getId()}">
                    <i class="icon-thumbs-up"></i>
                    <span>${comment.getLikeCount()}</span>
                </button>
                <button class="reaction-btn-sm" data-reaction="helpful" data-id="${comment.getId()}">
                    <i class="icon-heart"></i>
                    <span>${comment.getHelpfulCount()}</span>
                </button>
            </div>

            @if(canReply)
                <button class="reply-btn" data-comment-id="${comment.getId()}">Відповісти</button>
            @endif
        </div>

        <%-- Форма відповіді, спочатку прихована --%>
        <div id="reply-form-${comment.getId()}" class="reply-form" style="display:none;">
            <form action="/post/${postId}/comment" method="post">
                <input type="hidden" name="parentId" value="${comment.getId()}">
                <div class="form-group">
                    <textarea name="content" rows="2" required placeholder="Ваша відповідь..."></textarea>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-sm">Відправити</button>
                    <button type="button" class="btn btn-sm btn-text cancel-reply" data-comment-id="${comment.getId()}">Скасувати</button>
                </div>
            </form>
        </div>

        <%-- Рекурсивний виклик для відповідей, якщо вони є --%>
        @if(!comment.getReplies().isEmpty())
        <div class="comment-replies">
            <%-- Рекурсивне використання циклу для вкладених коментарів (c) --%>
            @for(Comment reply : comment.getReplies())
            <div id="comment-${reply.getId()}" class="comment reply">
                <div class="comment-header">
                    <div class="comment-author">
                        <img src="/images/avatars/${reply.getAuthor().getAvatar()}" alt="${reply.getAuthor().getUsername()}" class="avatar-small">
                        <div class="author-info">
                            <span class="author-name">${reply.getAuthor().getUsername()}</span>
                            <span class="comment-date" title="${reply.getCreatedAt().format(formatter)}">
                                            ${reply.getCreatedAt().format(formatter)}
                                        </span>
                        </div>
                    </div>
                </div>

                <div class="comment-content">
                    ${reply.getContent()}
                </div>