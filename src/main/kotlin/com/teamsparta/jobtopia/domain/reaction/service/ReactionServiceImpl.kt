package com.teamsparta.jobtopia.domain.reaction.service

import com.teamsparta.jobtopia.domain.comment.model.Comment
import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse
import com.teamsparta.jobtopia.domain.reaction.model.Reaction
import com.teamsparta.jobtopia.domain.reaction.model.ReactionType
import com.teamsparta.jobtopia.domain.reaction.repository.ReactionRepository
import com.teamsparta.jobtopia.domain.users.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReactionServiceImpl(
    private val reactionRepository: ReactionRepository,
    private val userRepository: UserRepository
): ReactionService {

    override fun updateReaction(userId: Long, post: Post?, comment: Comment?, reactionType: ReactionType) {
        val users = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        if (post != null) {
            val reaction = reactionRepository.findByPostIdAndReaction(post.id!!, reactionType)
            if (post.users.id == userId) throw IllegalArgumentException("본인이 작성한 글에는 남길 수 없습니다.")

            if (reaction == null) {
                reactionRepository.save(
                    Reaction(
                        users = users,
                        post = post,
                        reaction = reactionType
                    ))
            } else if (reactionType == reaction.reaction) reactionRepository.delete(reaction)

        } else if (comment != null) {
            val reaction = reactionRepository.findByCommentIdAndReaction(comment.id!!, reactionType)
            if (comment.user.id == userId) throw IllegalArgumentException("본인이 작성한 댓글에는 남길 수 없습니다.")

            if (reaction == null) {
                reactionRepository.save(
                    Reaction(
                        users = users,
                        comment = comment,
                        reaction = reactionType
                    ))
            } else if (reactionType == reaction.reaction) reactionRepository.delete(reaction)
        }
    }

    override fun deleteReaction(post: Post?, comment: Comment?) {
        if (post != null) reactionRepository.deleteAllByPostId(post.id!!)
        else if (comment != null) reactionRepository.deleteAllByCommentId(comment.id!!)
    }

    override fun getCountReactions(postId: Long?, commentId: Long?): ReactionResponse {
        var like = 0
        var disLike = 0

        if (postId != null) {
            like = reactionRepository.countByPostIdAndReaction(postId, ReactionType.LIKE)
            disLike = reactionRepository.countByPostIdAndReaction(postId, ReactionType.DISLIKE)
        } else if (commentId != null) {
            like = reactionRepository.countByCommentIdAndReaction(commentId, ReactionType.LIKE)
            disLike = reactionRepository.countByCommentIdAndReaction(commentId, ReactionType.DISLIKE)
        }

        return ReactionResponse(like, disLike)
    }
}