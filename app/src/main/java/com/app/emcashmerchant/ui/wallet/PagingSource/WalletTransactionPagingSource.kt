package com.app.emcashmerchant.ui.wallet.PagingSource

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.GroupedWalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiServices
import java.lang.Exception

class WalletTransactionPagingSource(val api: ApiServices,val accesToken:String) :
    PagingSource<Int, GroupedWalletTransactionResponse.Data.Row>() {
    override fun getRefreshKey(state: PagingState<Int, GroupedWalletTransactionResponse.Data.Row>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupedWalletTransactionResponse.Data.Row> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response = api.walletGropedTransactionResponse("Bearer $accesToken",nextPage,10).body()
        var groupedActivities= listOf<GroupedWalletTransactionResponse.Data.Row>()
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