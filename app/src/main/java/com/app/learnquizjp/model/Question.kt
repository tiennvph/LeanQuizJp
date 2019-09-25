package com.app.learnquizjp.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Question : Serializable {
    @SerializedName("qzcode")
    @Expose
    var qzcode: Int = 0
    @SerializedName("qzcontent")
    @Expose
    var qzcontent: String? = null
    @SerializedName("ascortect")
    @Expose
    var ascortect: String? = null
    @SerializedName("asincortecT1")
    @Expose
    var asincortecT1: String? = null
    @SerializedName("asincortecT2")
    @Expose
    var asincortecT2: String? = null
    @SerializedName("asincortecT3")
    @Expose
    var asincortecT3: String? = null
    @SerializedName("explain")
    @Expose
    var explain: String? = null
    @SerializedName("csfykindcode")
    @Expose
    var csfykindcode: Int = 0
    @SerializedName("csfylvcode")
    @Expose
    var csfylvcode: Int = 0
    @SerializedName("qzstatuschk")
    @Expose
    var qzstatuschk: Int = 0
    @SerializedName("ascurrent")
    @Expose
    var ascurrent: String? = null

}
