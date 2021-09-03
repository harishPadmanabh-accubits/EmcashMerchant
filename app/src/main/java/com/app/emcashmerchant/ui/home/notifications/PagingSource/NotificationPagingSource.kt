package com.app.emcashmerchant.ui.home.notifications.PagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.emcashmerchant.data.models.GroupedNotificationResponse
import com.app.emcashmerchant.data.network.ApiServices


class NotificationPagingSource(val api: ApiServices, val accesToken:String) :
    PagingSource<Int, GroupedNotificationResponse.Data.Row>() {
    override fun getRefreshKey(state: PagingState<Int, GroupedNotificationResponse.Data.Row>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupedNotificationResponse.Data.Row> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response = api.groupNotification("Bearer $accesToken",nextPage,10).body()
        var groupedActivities= listOf<GroupedNotificationResponse.Data.Row>()
        response?.data?.rows.let {
            if (it != null) {
                groupedActivities = it
            }

        }

        return LoadResult.Page(
            data = groupedActivities,
            prevKey = if (nextPage == 1) null else nextPage - 1,
            nextKey =if(nextPage==response?.data?.totalPages) null else response?.data?.page?.plus(1)
        )
    }

    companion object
    {
        private const val FIRST_PAGE_INDEX=1
    }

}