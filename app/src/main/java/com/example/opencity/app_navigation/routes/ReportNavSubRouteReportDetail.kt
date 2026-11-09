package com.example.opencity.app_navigation.routes

import com.example.opencity.app_navigation.SubLevelRoute

data class ReportNavSubRouteReportDetail(
    val reportId: Int
) : SubLevelRoute {
    override val returnableTo = false
}
