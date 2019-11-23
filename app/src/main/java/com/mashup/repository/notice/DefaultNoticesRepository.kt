package com.mashup.repository.notice

import com.mashup.model.Notice
import com.mashup.model.VoteStatus
import com.mashup.repository.notice.remote.NoticesRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Flowable

class DefaultNoticesRepository(
        private val noticesRemoteDataSource: NoticesRemoteDataSource
) : NoticesRepository {

    private var cachedNotices = emptyList<Notice>()
    private var mCacheIsDirty = false

    override fun getNoticeList(forceRefresh: Boolean): Flowable<List<Notice>> {

        if (!forceRefresh && cachedNotices.isNotEmpty() && !mCacheIsDirty) {
            return Flowable.fromArray(cachedNotices)
        }

        return noticesRemoteDataSource
                .getNoticeList()
                .flatMap { response ->
                    Flowable.fromArray(response.results).map {
                        it.reversed()
                    }.doOnNext {
                        cachedNotices = it
                    }
                }
                .doOnComplete({ mCacheIsDirty = false })
    }

    override fun updateNoticeAttendance(token: String, userId: Int, voteStatus: VoteStatus): Completable {
        return noticesRemoteDataSource.updateNoticeAttendance(token, userId, voteStatus)
    }
}
