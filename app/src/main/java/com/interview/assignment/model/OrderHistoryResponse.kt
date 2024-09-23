package com.interview.assignment.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class OrderHistoryResponse(
    val id: Int,
    val longitude: Double? = 0.0,
    val paymentMethod: Int,
    val latitude: Double? = 0.0,
    val companyId: Int,
    val driverId: String,
    val price: Double,
    val orderDate: String,
    val acceptedDate: String,
    val pickedDate: String,
    val arriveDate: String,
    val meals: List<Meals>? = arrayListOf(),
//    val deals : String,
    val estimationTime: Int,
    val driverPrice: Int,
    val restaurantPrice: Double,
    val address: String,
    val withDriver: Boolean,
    val restaurantIds: String,
    val discount: String,
    val couponCode: String,
    val restaurantId: Int,
    val startPrepare: String,
    val receiptCode: String,
    val transactionCode: String,
    val operatingSystem: String,
    val deviceType: String,
    val rejectedDateByRestaurant: String,
    val rejectReason: String,
    val driverName: String,
    val driverPhoneNumber: String,
    val driverImage: String,
    val timeToUser: Int,
    val userFeedBack: Boolean,
    val donePrepare: String,
    val driverDescription: String,
    val appVersion: String,
    val creditCardToken: String,
    val cardYear: String,
    val cardMonth: String,
    val phoneNumber: String,
    val userName: String,
    val email: String,
    val payRestaurant: Int,
    val getFromClient: Int,
    val deliveryType: Int,
    val restaurantDetail: RestaurantDetail,
    val tokenCardType: Int,
    val areaId: String,
    val locationAccuracy: String,
    val serviceFee: Int,
    val paymentMethodId: String,
    val deliveryFeeDetails: DeliveryFeeDetails,
    val l4digits: String,
    val currencyData: CurrencyData,
    val isCibus: Boolean,
    val remakeDetails: String,
    val isRemake: Boolean,
    val isCanceled: Boolean,
    val cancellationReason: String,
    val cocaColaCampaignDetails: CocaColaCampaignDetails,
    val productReplacementSuggestions: List<String>,
    val customerNotes: String,
    val posOrderId: String,
    val credits: Double? = 0.0,
//    val scheduledOrderDetails: String,
//    val invoices: List<String>,
    val referenceNumber: String,
    val enableReorder: Boolean
) : Parcelable

@Keep
@Parcelize
data class Meals(

    val id: Int,
    val quantity: Int,
    val mealContents: List<MealContents>,
    val smallImage: String,
    val price: Int,
    val serverImage: String,
    val blurhash: String,
    val name: String,
    val name_AR: String,
    val name_EN: String,
    val name_HE: String,
    val name_FR: String,
    val namesDictionary: String,
    val isPizza: Boolean,
    val prioirty: Int,
    val mealTypePriority: Int,
    val isPartialMealContentPricing: Boolean,
    val finalPriceToDisplay: Int,
    val finalPriceOfExtrasToDisplay: Int,
    val finalPriceWithExtrasToDisplay: Int
) : Parcelable

@Keep
@Parcelize
data class MealContents(

    val id: Int,
    val type: Int,
    val price: Int,
    val smallImageUrl: String,
    val serverImageUrl: String,
    val name: String,
    val name_AR: String,
    val name_EN: String,
    val name_HE: String,
    val name_FR: String,
    val qty: Int,
    val mealContentType: Int,
    val group: Int,
    val priority: Int,
    val hideExtra: Boolean,
    val isPizza: Boolean,
    val mealContentPrinting: Int
) : Parcelable

@Keep
@Parcelize
data class CocaColaCampaignDetails(
    val hasCocaColaBrand: Boolean,
    val isDone: Boolean
) : Parcelable

@Keep
@Parcelize
data class CurrencyData(

    val id: String,
    val symbol: String,
    val names: Names
) : Parcelable

@Keep
@Parcelize
data class DeliveryFeeDetails(

    val taskCost: Int,
    val userFee: Int,
    val originalUserFee: Int,
//    val userExtraFees: List<String>,
    val restaurantFeeCoverage: Double? = 0.0,
    val haatMarketingFeeCoverage: Double? = 0.0,
    val haatOperationFeeCoverage: Double? = 0.0,
    val userPricingModel: Double? = 0.0,
    val driverPricingModel: Double? = 0.0,
) : Parcelable

@Keep
@Parcelize
data class Details(

    val id: Int,
    val name: String,
    val name_AR: String,
    val name_HE: String,
    val name_EN: String,
    val address: String,
    val address_AR: String,
    val address_HE: String,
    val address_EN: String,
    val latitude: Double,
    val longitude: Double,
    val havePrivateDelivery: Boolean,
    val phoneNumber: String,
    val priority: Int,
    val restaurantCategoryId: Int,
    val images: List<Images>,
    val workingHours: List<WorkingHours>,
    val icon: String,
    val smallIcon: String,
    val status: Int,
    val type: String
) : Parcelable


@Keep
@Parcelize
data class DynamicWeightDetails(

    val quantityType: Int,
    val avgWeightPerItem: String,
    val pricePerWeight: String,
    val weightToPresent: String,
    val unitDetails: UnitDetails
) : Parcelable


@Keep
@Parcelize
data class UnitDetails(

    val stepSize: Double,
    val unitType: Int,
) : Parcelable


@Keep
@Parcelize
data class Images(

    val id: Int,
    val serverImageUrl: String,
    val smallImageUrl: String,
    val priority: Int,
) : Parcelable


@Keep
@Parcelize
data class Name(
    @SerializedName("ar")
    val ar: String,
    @SerializedName("en-US")
    val enUS: String,
    @SerializedName("he")
    val he: String
) : Parcelable

@Keep
@Parcelize
data class Names(
    @SerializedName("ar")
    val ar: String,
    @SerializedName("en-US")
    val enUS: String,
    @SerializedName("he")
    val he: String
) : Parcelable

@Keep
@Parcelize
data class ProductImages(

    val id: Int,
    val priority: Int,
    val serverImageUrl: String,
    val smallImageUrl: String,
    val blurhash: String
) : Parcelable


@Keep
@Parcelize
data class Products(

    val id: Int,
    val name: Name,
    val productImages: List<ProductImages>,
    val discountPercentage: Int,
    val discountPrice: Double,
    val price: Double,
    val quantity: Int,
//    val orderProductColors: String,
//    val orderProductSizes: String,
//    val orderProductFeatures: List<String>,
    val orderId: Int,
    val productId: Int,
    val supportDynamicPricing: Boolean,
//    val orderProductWeightUpdate: String,
    val dynamicWeightDetails: DynamicWeightDetails,
    val notAvailable: String,
//    val productReplacement: String,
    val isBigItem: Boolean,
    val finalWeight: Double? = 0.0,
    val finalPrice: Int,
    val finalWeightToDisplay: Double? = 0.0,
    val finalPriceToDisplay: Double,
    val strikedPriceToDisplay: String,
    val finalPriceOfExtrasToDisplay: Int,
    val finalPriceWithExtrasToDisplay: Double,
    val quantityTypeToDisplay: Int,
    val unitTypeToDisplay: Int,
    val weightBeforeUpdateToDisplay: String,
    val priceBeforeUpdateToDisplay: String,
    val strikedPriceBeforeUpdateToDisplay: String,
    val dealProduct: DealProduct,
    val orderDealProductId: String
) : Parcelable


@Keep
@Parcelize
data class DealProduct(

    val orderMarketDealId: Int,
    val marketDealId: Int,
    val dealQuantity: Int,
    val dealPrice: Double,
    val productOriginalPrice: Double,
    val quantityToDisplay: Double,
    val unitTypeToDisplay: String
) : Parcelable

@Keep
@Parcelize
data class RestaurantDetail(

    val details: Details,
    val meals: List<Meals>? = arrayListOf(),
//    val deals : List<String>,
    val products: List<Products>
) : Parcelable

@Keep
@Parcelize
data class WorkingHours(

    val dayOfWeek: Int,
    val fromHour: Int,
    val toHour: Int,
    val fromHourDate: String,
    val toHourDate: String
) : Parcelable