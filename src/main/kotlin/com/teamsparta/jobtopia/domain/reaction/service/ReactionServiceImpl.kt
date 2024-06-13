package com.teamsparta.jobtopia.domain.reaction.service

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

    override fun updatePostReaction(userId: Long, post: Post, reactionType: ReactionType) {
        val reaction = reactionRepository.findByPostIdAndReaction(post.id!!, reactionType)
        val users = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        if (post.users.id == userId) throw IllegalArgumentException("본인 작성글에는 남길 수 없습니다.")

        if (reaction == null) {
            reactionRepository.save(
                Reaction(
                    users = users,
                    post = post,
                    reaction = reactionType
                ))
        } else if (reactionType == reaction.reaction) reactionRepository.delete(reaction)

    }

    override fun deletePostReaction(post: Post) {
        reactionRepository.deleteAllByPostId(post.id!!)
    }

    override fun getCountPostReactions(postId: Long): ReactionResponse {
        val like = reactionRepository.countByPostIdAndReaction(postId, ReactionType.LIKE)
        val disLike = reactionRepository.countByPostIdAndReaction(postId, ReactionType.DISLIKE)

        return ReactionResponse(like, disLike)
    }
}