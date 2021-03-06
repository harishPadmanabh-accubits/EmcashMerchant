package com.app.emcashmerchant.ui.viewAllTransaction.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.emcashmerchant.data.model.response.RecentTransactionResponse
import com.app.emcashmerchant.data.network.EmCashApiServices

class ViewAllTransactionPagingSource(val api: EmCashApiServices, val accesToken:String) :
    PagingSource<Int, RecentTransactionResponse.Data.Row>() {
    override fun getRefreshKey(state: PagingState<Int, RecentTransactionResponse.Data.Row>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecentTransactionResponse.Data.Row> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response = api.getAllTransactedUsers(nextPage,10).body()
        var groupedActivities= listOf<RecentTransactionResponse.Data.Row>()
        response?.data?.rows.let {
            if (it != null) {
                groupedActivities = it
            }

        }

        return LoadResult.Page(
            data = groupedActivities,
            prevKey = if (response?.data?.totalPages == 0) null else {
                if (nextPage == 1) null else nextPage - 1
            },
            nextKey = if (response?.data?.totalPages == 0) null else {
                if (nextPage == response?.data?.totalPages) null else response?.data?.page?.plus(1)

            }
        )
    }

    companion object
    {
        private const val FIRST_PAGE_INDEX=1
    }
}