package com.app.emcashmerchant.ui.transaction_history.PagingSource

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.emcashmerchant.data.models.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.data.models.GroupedWalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiServices
import com.app.emcashmerchant.ui.wallet.PagingSource.WalletTransactionPagingSource

class AllTransactionsPagingSource(
    val api: ApiServices,
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
        val response = api.allGroupedTransactionHistoryReponse(
            "Bearer ${accesToken}",
            nextPage,
            10,
            mode,
            startDate,
            endDate, status,
            type
        ).body()
        var groupedActivities= listOf<GroupedTransactionHistoryResponse.Data.Row>()
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

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}