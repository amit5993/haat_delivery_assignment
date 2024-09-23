package com.interview.assignment.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class DynamicResponse(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("data")
    var data: Any? = null,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("code")
    var code: Int = 0
)

@Keep
@Parcelize
data class UserData(
    var login: String? = "",
    var id: Int? = 0,
    var following: Int? = 0,
    var followers: Int? = 0,
    var created_at: String? = "",
    var location: String? = "",
    var node_id: String? = "",
    var gravatar_id: String? = "",
    var url: String? = "",
    var email: String? = "",
    var repos_url: String? = "",
    var avatar_url: String? = ""
) : Parcelable

@Keep
@Parcelize
data class MqttResponse(
    var userName: String? = "",
    var clientId: String? = "",
    var password: String? = "",
    var topic: String? = "",
    var host: String? = "",
    var port: Int? = 0,
    var qos: Int? = 0,
    var protocol: String? = ""
) : Parcelable

@Keep
@Parcelize
data class Message(
    var text: String? = ""
) : Parcelable

@Keep
@Parcelize
data class RestaurantResponse(
    val isInOperating: Boolean? = false,
    val closestArea: String? = "",
    val areasContainer: AreasContainer? = null,
    val appSettings: AppSettings? = null,
    val areaSettings: AreaSettings? = null,
    val tags: Tags? = null,
    val mainPageBanners: MainPageBanners? = null,
    val categories: List<Categories>? = arrayListOf()
) : Parcelable

@Keep
@Parcelize
data class Categories(
    val title: String? = "",
    val maxRows: Int? = 0,
    val id: Int? = 0,
    val priority: Int? = 0,
    val elementType: String? = "",
    val name: String? = "",
    val backgroundImage: BackgroundImage? = null,
    val topImage: TopImage? = null,
    val topImageType: Int? = 0,
    val isSponsored: Boolean? = false,
    val viewAll: Boolean? = false,
    val backgroundColor: String? = "",
    val stores: List<Stores>? = arrayListOf()
) : Parcelable

@Keep
@Parcelize
data class Icon(
    val serverImage: String? = "",
    val smallServerImage: String? = "",
    val blurhashImage: String? = ""
) : Parcelable

@Keep
@Parcelize
data class Stores(
    val storeId: Int? = 0,
    val name: String? = "",
    val address: String? = "",
    val opacity: Double? = 0.0,
    val distance: Double? = 0.0,
    val icon: Icon? = null,
    val rating: Rating? = null,
    val labels: List<Labels>? = listOf(),
    val closestWorkingHour: String? = "",
    val is24Hours: Boolean? = false,
    val priority: Int? = 0,
    val status: Int? = 0,
    val isNew: Boolean? = false,
    val zoneId: String? = "",
    val recommendedPriority: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class Labels(
    val text: String? = "",
    val labelType: String? = ""
) : Parcelable

@Keep
@Parcelize
data class Rating(
    val value: Double? = 0.0,
    val numberOfRatings: String? = ""
) : Parcelable

@Keep
@Parcelize
data class TopImage(
    val serverImage: String? = "",
    val smallServerImage: String? = "",
    val blurhashImage: String? = ""
) : Parcelable

@Keep
@Parcelize
data class BackgroundImage(
    val serverImage: String? = "",
    val smallServerImage: String? = "",
    val blurhashImage: String? = ""
) : Parcelable

@Keep
@Parcelize
data class MainPageBanners(
    val interval: Int? = 0,
    val banners: List<String>? = listOf()
) : Parcelable

@Keep
@Parcelize
data class AllAreas(
    val areaId: Int? = 0,
    val coordinates: List<Coordinates>? = listOf(),
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val distance: Double? = 0.0,
    val name: String? = "",
    val countryId: Int? = 0,
    val isNew: Boolean? = false,
    val isReadyArea: Boolean? = false,
    val isMarketReady: Boolean? = false,
    val isRestaurantReady: Boolean? = false
) : Parcelable

@Keep
@Parcelize
data class Coordinates(
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0
) : Parcelable

@Keep
@Parcelize
data class AreasContainer(
    val areasInCountry: List<AreasInCountry>? = listOf(),
    val areasOutOfCountry: List<AreasOutOfCountry>? = listOf(),
    val allAreas: List<AllAreas>? = listOf()
) : Parcelable

@Keep
@Parcelize
data class AreasInCountry(
    val areaId: Int? = 0,
    val coordinates: List<Coordinates>? = listOf(),
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val distance: Double? = 0.0,
    val name: String? = "",
    val countryId: Int? = 0,
    val isNew: Boolean? = false,
    val isReadyArea: Boolean? = false,
    val isMarketReady: Boolean? = false,
    val isRestaurantReady: Boolean? = false
) : Parcelable

@Keep
@Parcelize
data class AreasOutOfCountry(
    val areaId: Int? = 0,
    val coordinates: List<Coordinates>? = listOf(),
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val distance: Double? = 0.0,
    val name: String? = "",
    val countryId: Int? = 0,
    val isNew: Boolean? = false,
    val isReadyArea: Boolean? = false,
    val isMarketReady: Boolean? = false,
    val isRestaurantReady: Boolean? = false
) : Parcelable

@Keep
@Parcelize
data class AppSettings(
    val version: Version? = null,
    val customerServicePhone: Int? = 0,
    val imageServer: String? = "",
    val currency: Currency? = null,
    val appUpdateSettings: AppUpdateSettings? = null
) : Parcelable

@Keep
@Parcelize
data class Currency(
    val currencyId: Int? = 0,
    val symbol: String? = "",
    val name: String? = ""
) : Parcelable

@Keep
@Parcelize
data class AppUpdateSettings(
    val forceUpdateSettings: ForceUpdateSettings? = null,
    val regularUpdateSettings: RegularUpdateSettings? = null
) : Parcelable

@Keep
@Parcelize
data class RegularUpdateSettings(
    val messageTitle: String? = "",
    val messageBody: String? = "",
    val androidVersion: Int? = 0,
    val iosVersion: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class ForceUpdateSettings(
    val messageTitle: String? = "",
    val messageBody: String? = "",
    val androidVersion: Int? = 0,
    val iosVersion: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class Version(
    val iosMinVersion: Int? = 0,
    val androidMinVersion: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class AreaSettings(
    val areaStatus: Int? = 0,
    val isAnnouncement: Boolean? = false,
    val announcement: Announcement? = null,
    val restrictionMessage: RestrictionMessage? = null,
    val pickupWorkingHours: List<PickupWorkingHours>? = listOf(),
    val deliveryWorkingHours: List<DeliveryWorkingHours>? = listOf(),
    val aggregatedDeliveryWorkingHours: List<AggregatedDeliveryWorkingHours>? = listOf(),
    val aggregatedPickupWorkingHours: List<AggregatedPickupWorkingHours>? = listOf()
) : Parcelable

@Keep
@Parcelize
data class AggregatedPickupWorkingHours(
    val fromDay: Int? = 0,
    val toDay: Int? = 0,
    val fromHour: Int? = 0,
    val toHour: Int? = 0,
    val type: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class AggregatedDeliveryWorkingHours(
    val fromDay: Int? = 0,
    val toDay: Int? = 0,
    val fromHour: Int? = 0,
    val toHour: Int? = 0,
    val type: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class RestrictionMessage(
    val title: String? = "",
    val message: String? = ""
) : Parcelable

@Keep
@Parcelize
data class PickupWorkingHours(
    val dayOfWeek: Int? = 0,
    val from: Int? = 0,
    val to: Int? = 0,
    val type: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class DeliveryWorkingHours(
    val dayOfWeek: Int? = 0,
    val from: Int? = 0,
    val to: Int? = 0,
    val type: Int? = 0
) : Parcelable

@Keep
@Parcelize
data class Announcement(
    val title: String? = "",
    val message: String? = ""
) : Parcelable

@Keep
@Parcelize
data class Tags(
    val title: String? = "",
    val tags: List<Tag>? = listOf()
) : Parcelable

@Keep
@Parcelize
data class Tag(
    val id: Int? = 0,
    val name: String? = "",
    val images: Icon,
    val backgroundColor: String? = "",
) : Parcelable
