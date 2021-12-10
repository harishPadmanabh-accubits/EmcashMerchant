package com.app.emcashmerchant.ui.transactionHistory.PagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.emcashmerchant.data.model.response.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.data.network.EmCashApiServices

class AllTransactionsPagingSource(
    val api: EmCashApiServices,
    val accesToken: String,
    val mode: String,
    var startDate: String,
    var endDate: String,
    var status: String,
    var type: String
) :
    PagingSource<Int, GroupedTransactionHistoryResponse.Data.Row>() {
    override fun getRefreshKey(state: PagingState<Int, GroupedTransactionHistoryResponse.Data.Row>): Int? {
        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupedTransactionHistoryResponse.Data.Row> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response = api.getTransactionHistory(
            nextPage,
            10,
            mode,
            startDate,
            endDate, status,
            type
        ).body()
        var groupedActivities = listOf<GroupedTransactionHistoryResponse.Data.Row>()
        response?.data?.rows.let {
            if (it != null) {
                groupedActivities = it
            }

        }


        if (response != null) {
            return LoadResult.Page(
                data = groupedActivities,
                prevKey = if (response.data.totalPages == 0) null else {
                    if (nextPage == 1) null else nextPage - 1
                },
                nextKey = if (response.data.totalPages == 0) null else {
                    if (nextPage == response.data.totalPages) null else response.data.page.plus(1)

                }
            )
        } else
            return LoadResult.Error(Error("null response error"))
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}