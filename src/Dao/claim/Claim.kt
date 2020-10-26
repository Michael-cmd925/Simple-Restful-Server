package org.csuf.cspc411.Dao.person

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

data class Claim (var title:String?, var date: String?){
    var id : UUID = UUID.randomUUID()
    var isSolved : Boolean = false

}
fun main() {


}