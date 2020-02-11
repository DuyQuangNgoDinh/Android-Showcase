package progtips.vn.fbstyle.ui.helper

import progtips.vn.fbstyle.model.Comment

class CommentList {
    companion object {

        fun initCommentList(): List<Comment> {
            return mutableListOf<Comment>().apply {
                add(Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Kenneth Lim", 1581331292000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."))
                add(Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Johnny Woo", 1581406508000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit...",
                    listOf(
                        Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Johnny Woo", 1581406508000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit...")
                    )))
                add(Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Tong Hua", 1581233708000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit...",
                    listOf(
                        Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Johnny Woo", 1581406508000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."),
                        Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Johnny Woo", 1581406508000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit...")
                    )))
                add(Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Jackie Chan", 1549697708000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit...",
                    listOf(
                        Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Johnny Woo", 1581406508000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."),
                        Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Johnny Woo", 1581406508000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit..."),
                        Comment("https://i.ya-webdesign.com/images/home-icon-png-transparent.png", "Johnny Woo", 1581406508000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer porta felis dolor, vitae auctor magna congue eu. Aenean sollicitudin arcu ut orci semper blandit...")
                    )))
            }
        }
    }
}