package progtips.vn.fbstyle.ui.helper

import progtips.vn.fbstyle.model.Comment

class CommentList {
    companion object {

        fun initCommentList(): List<Comment> {
            return mutableListOf<Comment>().apply {
                add(Comment(null, "Kenneth Lim", 1581331292, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."))
                add(Comment(null, "Kenneth Lim", 1581331292, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."))
                add(Comment(null, "Kenneth Lim", 1581331292, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."))
                add(Comment(null, "Kenneth Lim", 1581331292, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."))
            }
        }
    }
}