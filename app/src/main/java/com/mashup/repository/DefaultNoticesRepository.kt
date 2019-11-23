package com.mashup.repository

import com.mashup.model.Notice
import com.mashup.model.VoteStatus
import com.mashup.repository.source.remote.RepositoriesRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Flowable

class DefaultNoticesRepository(
        private val noticesRemoteDataSource: RepositoriesRemoteDataSource
) : NoticesRepository {

    private var cachedNotices = emptyList<Notice>()
    private var mCacheIsDirty = false

    override fun getNoticeList(forceRefresh: Boolean): Flowable<List<Notice>> {

        if (!forceRefresh && cachedNotices.isNotEmpty() && !mCacheIsDirty) {
            return Flowable.fromArray(cachedNotices)
        }

        return noticesRemoteDataSource
                .getNoticeList()
                .flatMap { notices ->
                    Flowable.fromArray(notices).doOnNext {
                        cachedNotices = notices
                    }
                }
                .doOnComplete({ mCacheIsDirty = false })
    }

    override fun updateNoticeAttendance(userId: Int, voteStatus: VoteStatus): Completable {
        return noticesRemoteDataSource.updateNoticeAttendance(userId, voteStatus)
    }
}
