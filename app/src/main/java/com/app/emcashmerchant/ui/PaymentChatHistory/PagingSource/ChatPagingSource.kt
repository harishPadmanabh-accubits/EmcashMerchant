package com.app.emcashmerchant.ui.PaymentChatHistory.PagingSource

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.emcashmerchant.data.models.GroupedChatHistoryResponse
import com.app.emcashmerchant.data.models.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.data.models.GroupedWalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiServices
import com.app.emcashmerchant.ui.wallet.PagingSource.WalletTransactionPagingSource

class ChatPagingSource(
    val api: ApiServices,
    val accesToken: String,
    val userId: Int
) :
    PagingSource<Int, GroupedChatHistoryResponse.Data.Row>() {
    override fun getRefreshKey(state: PagingState<Int, GroupedChatHistoryResponse.Data.Row>): Int? {
        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupedChatHistoryResponse.Data.Row> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response = api.getPagingGroupedChatResponse(
            "Bearer ${accesToken}", userId,
            nextPage,
            10
        ).body()
        var groupedActivities = listOf<GroupedChatHistoryResponse.Data.Row>()
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