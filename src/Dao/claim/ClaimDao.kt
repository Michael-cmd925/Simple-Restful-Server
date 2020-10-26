package org.csuf.cspc411.Dao.person

import org.csuf.cspc411.Dao.Dao
import org.csuf.cspc411.Dao.Database
import java.util.*

class ClaimDao : Dao() {

    fun add(pObj : Claim) {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare the sql statement
        sqlStmt = "insert into claim (id, title, date, isSolved) values ('${(pObj.id).toString()}', '${pObj.title}', '${pObj.date}', '${pObj.isSolved}' )"

        // 3. submit the sql statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare the sql statement
        sqlStmt = "select id, title, date, isSolved from claim"

        // 3. submit the sql statement
        var claimList: MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)
        var index = 0
        // 4. Convert into Kotlin object format
        while (st?.step()!!) {
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnInt(3)
            claimList.add(Claim(title, date))
            claimList[index].id = UUID.fromString(id)
            claimList[index].isSolved = isSolved == 1
            index += 1
        }
        return claimList
    }
}