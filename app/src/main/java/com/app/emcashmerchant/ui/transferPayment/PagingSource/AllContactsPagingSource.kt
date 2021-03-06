package com.app.emcashmerchant.ui.transferPayment.PagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.emcashmerchant.data.model.response.GroupedContactsResponse
import com.app.emcashmerchant.data.network.EmCashApiServices


class AllContactsPagingSource(
    val api: EmCashApiServices,
    val accesToken: String,
    var search: String

) :
    PagingSource<Int, GroupedContactsResponse.Data.Row>() {
    override fun getRefreshKey(state: PagingState<Int, GroupedContactsResponse.Data.Row>): Int? {
        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupedContactsResponse.Data.Row> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response = api.getAllContactsPaged(
            nextPage,
            10, search
        ).body()
        var groupedActivities = listOf<GroupedContactsResponse.Data.Row>()
        response?.data?.rows.let {
            if (it != null) {
                groupedActivities = it
            }

        }


        return LoadResult.Page(
            data = groupedActivities,
            prevKey = if (nextPage == 1) null else nextPage - 1,
            nextKey = if (nextPage == response?.data?.totalPages) null else response?.data?.page?.plus(
                1
            )
        )
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}