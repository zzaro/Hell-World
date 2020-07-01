package com.example.porte.ui.etc

import com.example.porte.R

interface EtcContent {
    val imageId: Int
}

//ShopBanner data class 선언(ShopContent 인터페이스 구현)
data class EtcBanner(
    override val imageId: Int
) : EtcContent

//ShopItem data class 선언(ShopContent 인터페이스 구현)
data class EtcItem(
    override val imageId: Int,
    val itemName: String,
    val itemInfo: String
) : EtcContent

/* RecyclerView의 item에 사용할 enum class 선언
   - shopType: BANNER(0), ITEM(1) 구분 (Int는 companion object { val BANNER = 0 val ITEM = 1 }로 선언)
   - shopContent: item 컨텐츠(ShopBanner 또는 ShopItem)
 */
enum class EtcData(val etcType: Int, val etcContent: EtcContent) {
    ETC_ITEM1(EtcRecyclerAdapter.BANNER, EtcBanner(R.drawable.banner1)),
    ETC_ITEM2(EtcRecyclerAdapter.ITEM, EtcItem(R.drawable.amenity, "편의시설", "편안함과 쾌적함을 제공합니다")),
    ETC_ITEM3(EtcRecyclerAdapter.ITEM, EtcItem(R.drawable.duty_free, "온라인 면세점", " 한ㆍ중ㆍ일ㆍ영 인터넷면세점을 통해 최고의 상품과 서비스를 제공합니다")),
    ETC_ITEM4(EtcRecyclerAdapter.BANNER, EtcBanner(R.drawable.banner2)),
    ETC_ITEM5(EtcRecyclerAdapter.ITEM, EtcItem(R.drawable.restaurant, "식당안내", "인천공항에서의 즐거운 경험을 위한 다양한 식음료 매장을 안내합니다.")),
    ETC_ITEM6(EtcRecyclerAdapter.ITEM, EtcItem(R.drawable.festival, "공항 이벤트", " 공항에서 즐기는 다양한 프로모션!"));
}