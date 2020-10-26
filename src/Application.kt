package org.csuf.cspc411

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import org.csuf.cspc411.Dao.person.Claim
import org.csuf.cspc411.Dao.person.ClaimDao

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    routing {
        this.get("/ClaimService/add"){
            println("HTTP message is using GET method with /get")
            val title : String? =  call.request.queryParameters["Title"]
            val date : String? =  call.request.queryParameters["Date"]
            val response = String.format("Title: %s  Date: %s", title, date)
            //
            val pObj = Claim(title,date)
            val dao = ClaimDao().add(pObj)
            call.respondText(response, status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)

        }

        get("/ClaimService/getAll"){
            val pList = ClaimDao().getAll()
            println("The number of claims: ${pList.size}")
            // JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(pList)
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }

        post("/ClaimService/add"){
            //message body data
            val contTyp = call.request.contentType()
            val data = call.request.receiveChannel() //similar to input string
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            //convert byte array to string
            val str : String = String(output) // for further processing

            var jObj = Gson().fromJson(str, Claim::class.java)

            var cObj1 : Claim = Claim(jObj.title, jObj.date)
            val pObj = ClaimDao().add(cObj1)

            println("HTTP message is using POST method with /post ${contTyp} ${str}")
            call.respondText("The Post was successfully processed.", status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)

        }



    }



}

