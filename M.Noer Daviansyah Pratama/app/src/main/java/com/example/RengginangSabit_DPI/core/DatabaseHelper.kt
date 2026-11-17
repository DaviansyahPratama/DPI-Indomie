package com.example.RengginangSabit_DPI.core

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.RengginangSabit_DPI.monitoring.ProductionLine
import com.example.RengginangSabit_DPI.orders.Order

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "rengginang_sabit.db"
        private const val DATABASE_VERSION = 1

        // 1. TABEL USERS
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_PHONE = "phone"
        const val COLUMN_USER_USERNAME = "username"
        const val COLUMN_USER_PASSWORD = "password"

        // 2. TABEL ORDERS (CUSTOM TOPIK 1: PESANAN RENGGINANG)
        const val TABLE_ORDERS = "orders"
        const val COLUMN_ORDER_ID = "id"
        const val COLUMN_ORDER_CODE = "order_code"
        const val COLUMN_CUSTOMER_NAME = "customer_name"
        const val COLUMN_ORDER_DATE = "order_date"
        const val COLUMN_QUANTITY_KG = "quantity_kg"
        const val COLUMN_TOTAL_PRICE = "total_price"
        const val COLUMN_ORDER_STATUS = "status"

        // 3. TABEL PRODUCTION_LINES (CUSTOM TOPIK 2: LINI PRODUKSI & MONITORING)
        const val TABLE_PRODUCTION_LINES = "production_lines"
        const val COLUMN_LINE_ID = "id"
        const val COLUMN_LINE_NAME = "line_name"
        const val COLUMN_LINE_STATUS_DESC = "status_desc"
        const val COLUMN_LINE_OUTPUT_RATE = "output_rate"
        const val COLUMN_LINE_TEMPERATURE = "temperature"
        const val COLUMN_LINE_HUMIDITY = "humidity"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUsers = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_NAME TEXT,
                $COLUMN_USER_PHONE TEXT,
                $COLUMN_USER_USERNAME TEXT,
                $COLUMN_USER_PASSWORD TEXT
            )
        """.trimIndent()

        val createTableOrders = """
            CREATE TABLE $TABLE_ORDERS (
                $COLUMN_ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ORDER_CODE TEXT,
                $COLUMN_CUSTOMER_NAME TEXT,
                $COLUMN_ORDER_DATE TEXT,
                $COLUMN_QUANTITY_KG INTEGER,
                $COLUMN_TOTAL_PRICE TEXT,
                $COLUMN_ORDER_STATUS TEXT
            )
        """.trimIndent()

        val createTableProductionLines = """
            CREATE TABLE $TABLE_PRODUCTION_LINES (
                $COLUMN_LINE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_LINE_NAME TEXT,
                $COLUMN_LINE_STATUS_DESC TEXT,
                $COLUMN_LINE_OUTPUT_RATE TEXT,
                $COLUMN_LINE_TEMPERATURE TEXT,
                $COLUMN_LINE_HUMIDITY TEXT
            )
        """.trimIndent()

        db.execSQL(createTableUsers)
        db.execSQL(createTableOrders)
        db.execSQL(createTableProductionLines)

        seedInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTION_LINES")
        onCreate(db)
    }

    private fun seedInitialData(db: SQLiteDatabase) {
        val userValues = ContentValues().apply {
            put(COLUMN_USER_NAME, "Daviansyah Pratama")
            put(COLUMN_USER_PHONE, "081234567890")
            put(COLUMN_USER_USERNAME, "admin")
            put(COLUMN_USER_PASSWORD, "admin")
        }
        db.insert(TABLE_USERS, null, userValues)

        val initialOrders = listOf(
            ContentValues().apply {
                put(COLUMN_ORDER_CODE, "#ORD-2026-001")
                put(COLUMN_CUSTOMER_NAME, "Toko Berkah Jaya")
                put(COLUMN_ORDER_DATE, "15 Okt 2026")
                put(COLUMN_QUANTITY_KG, 50)
                put(COLUMN_TOTAL_PRICE, "Rp 2.500.000")
                put(COLUMN_ORDER_STATUS, "Diproses")
            },
            ContentValues().apply {
                put(COLUMN_ORDER_CODE, "#ORD-2026-002")
                put(COLUMN_CUSTOMER_NAME, "Agen Sembako Gurih")
                put(COLUMN_ORDER_DATE, "16 Okt 2026")
                put(COLUMN_QUANTITY_KG, 120)
                put(COLUMN_TOTAL_PRICE, "Rp 6.000.000")
                put(COLUMN_ORDER_STATUS, "Baru")
            },
            ContentValues().apply {
                put(COLUMN_ORDER_CODE, "#ORD-2026-003")
                put(COLUMN_CUSTOMER_NAME, "Distributor Rengginang Utama")
                put(COLUMN_ORDER_DATE, "14 Okt 2026")
                put(COLUMN_QUANTITY_KG, 200)
                put(COLUMN_TOTAL_PRICE, "Rp 10.000.000")
                put(COLUMN_ORDER_STATUS, "Selesai")
            }
        )
        for (order in initialOrders) {
            db.insert(TABLE_ORDERS, null, order)
        }

        val initialLines = listOf(
            ContentValues().apply {
                put(COLUMN_LINE_NAME, "Lini Produksi A")
                put(COLUMN_LINE_STATUS_DESC, "Pemasakan Stabil")
                put(COLUMN_LINE_OUTPUT_RATE, "45 kg/jam")
                put(COLUMN_LINE_TEMPERATURE, "74°C")
                put(COLUMN_LINE_HUMIDITY, "42%")
            },
            ContentValues().apply {
                put(COLUMN_LINE_NAME, "Lini Produksi B")
                put(COLUMN_LINE_STATUS_DESC, "Pembersihan Alat")
                put(COLUMN_LINE_OUTPUT_RATE, "0 kg/jam")
                put(COLUMN_LINE_TEMPERATURE, "28°C")
                put(COLUMN_LINE_HUMIDITY, "55%")
            },
            ContentValues().apply {
                put(COLUMN_LINE_NAME, "Lini Produksi C")
                put(COLUMN_LINE_STATUS_DESC, "Pencetakan Adonan")
                put(COLUMN_LINE_OUTPUT_RATE, "38 kg/jam")
                put(COLUMN_LINE_TEMPERATURE, "70°C")
                put(COLUMN_LINE_HUMIDITY, "40%")
            }
        )
        for (line in initialLines) {
            db.insert(TABLE_PRODUCTION_LINES, null, line)
        }
    }

    // 1. FITUR USERS REGISTER & LOGIN
    fun registerUser(name: String, phone: String, username: String, pass: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, name)
            put(COLUMN_USER_PHONE, phone)
            put(COLUMN_USER_USERNAME, username)
            put(COLUMN_USER_PASSWORD, pass)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun checkUserLogin(inputUser: String, pass: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE ($COLUMN_USER_USERNAME = ? OR $COLUMN_USER_PHONE = ?) AND $COLUMN_USER_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(inputUser, inputUser, pass))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // 2. CRUD TABEL ORDERS
    fun getAllOrders(): List<Order> {
        val list = mutableListOf<Order>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ORDERS ORDER BY $COLUMN_ORDER_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val dbId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID))
                val code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_CODE)) ?: ""
                val customer = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUSTOMER_NAME)) ?: ""
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE)) ?: ""
                val qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY_KG))
                val price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE)) ?: ""
                val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_STATUS)) ?: ""

                list.add(Order(dbId, code, customer, date, qty, price, status))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun insertOrder(code: String, customer: String, date: String, qty: Int, price: String, status: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ORDER_CODE, code)
            put(COLUMN_CUSTOMER_NAME, customer)
            put(COLUMN_ORDER_DATE, date)
            put(COLUMN_QUANTITY_KG, qty)
            put(COLUMN_TOTAL_PRICE, price)
            put(COLUMN_ORDER_STATUS, status)
        }
        return db.insert(TABLE_ORDERS, null, values)
    }

    fun updateOrder(dbId: Long, code: String, customer: String, date: String, qty: Int, price: String, status: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ORDER_CODE, code)
            put(COLUMN_CUSTOMER_NAME, customer)
            put(COLUMN_ORDER_DATE, date)
            put(COLUMN_QUANTITY_KG, qty)
            put(COLUMN_TOTAL_PRICE, price)
            put(COLUMN_ORDER_STATUS, status)
        }
        return db.update(TABLE_ORDERS, values, "$COLUMN_ORDER_ID = ?", arrayOf(dbId.toString()))
    }

    fun deleteOrder(dbId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_ORDERS, "$COLUMN_ORDER_ID = ?", arrayOf(dbId.toString()))
    }

    // 3. CRUD TABEL PRODUCTION_LINES
    fun getAllProductionLines(): List<ProductionLine> {
        val list = mutableListOf<ProductionLine>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCTION_LINES ORDER BY $COLUMN_LINE_ID ASC", null)
        if (cursor.moveToFirst()) {
            do {
                val dbId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LINE_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINE_NAME)) ?: ""
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINE_STATUS_DESC)) ?: ""
                val output = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINE_OUTPUT_RATE)) ?: ""
                val temp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINE_TEMPERATURE)) ?: ""
                val hum = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINE_HUMIDITY)) ?: ""

                list.add(ProductionLine(dbId, name, desc, output, temp, hum))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun insertProductionLine(name: String, desc: String, output: String, temp: String, hum: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LINE_NAME, name)
            put(COLUMN_LINE_STATUS_DESC, desc)
            put(COLUMN_LINE_OUTPUT_RATE, output)
            put(COLUMN_LINE_TEMPERATURE, temp)
            put(COLUMN_LINE_HUMIDITY, hum)
        }
        return db.insert(TABLE_PRODUCTION_LINES, null, values)
    }

    fun updateProductionLine(dbId: Long, name: String, desc: String, output: String, temp: String, hum: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LINE_NAME, name)
            put(COLUMN_LINE_STATUS_DESC, desc)
            put(COLUMN_LINE_OUTPUT_RATE, output)
            put(COLUMN_LINE_TEMPERATURE, temp)
            put(COLUMN_LINE_HUMIDITY, hum)
        }
        return db.update(TABLE_PRODUCTION_LINES, values, "$COLUMN_LINE_ID = ?", arrayOf(dbId.toString()))
    }

    fun deleteProductionLine(dbId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_PRODUCTION_LINES, "$COLUMN_LINE_ID = ?", arrayOf(dbId.toString()))
    }
}
