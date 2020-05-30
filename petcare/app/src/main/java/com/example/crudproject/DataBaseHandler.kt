package com.example.crudproject

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.os.Build
import android.provider.ContactsContract
import android.widget.Toast
//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    val allUsers: ArrayList<UserModel>
        get() {
            val userModelArrayList = ArrayList<UserModel>()

            val selectQuery = "SELECT  * FROM $TABLE_USER"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    val userModel = UserModel()
                    userModel.setIds(c.getInt(c.getColumnIndex(USER_ID)))
                    userModel.setNames(c.getString(c.getColumnIndex(USER_NAME)))
                    userModel.setEmails(c.getString(c.getColumnIndex(USER_EMAIL)))
                    userModel.setPasswords(c.getString(c.getColumnIndex(USER_PASSWORD)))

                    userModelArrayList.add(userModel)
                } while (c.moveToNext())
            }
            return userModelArrayList
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USER)
        db.execSQL(CREATE_TABLE_PRODUCT)
        db.execSQL(CREATE_TABLE_BASKET)
        db.execSQL(CREATE_TABLE_ORDER)
        db.execSQL(CREATE_TABLE_DETAIL_ORDER)
        db.execSQL(CREATE_TABLE_PRODUCT_SENDING)
        db.execSQL(CREATE_TABLE_MAIL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_USER'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_PRODUCT'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_BASKET'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_ORDER'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_DETAIL_ORDER'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_PRODUCT_SENDING'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_MAIL'")
        onCreate(db)
    }

    fun signUp(name: String, email: String, password: String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_NAME, name)
        values.put(USER_EMAIL, email)
        values.put(USER_PASSWORD, password)
        db.insert(TABLE_USER, null, values)

    }

    fun addProduct(name: String, prices: Int, stock: Int, image: ByteArray){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_NAME, name)
        values.put(PRODUCT_PRICES, prices)
        values.put(PRODUCT_STOCK, stock)
        values.put(PRODUCT_IMAGE, image)
        db.insert(TABLE_PRODUCT, null, values)
    }

    fun addBasket(userId: Int, productId: Int, totalProduct: Int, isChecked: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(BASKET_USER_ID, userId)
        values.put(BASKET_PRODUCT_ID, productId)
        values.put(BASKET_TOTAL_PRODUCT, totalProduct)
        values.put(BASKET_ISCHECKED, isChecked)
        db.insert(TABLE_BASKET, null, values)
    }

    fun addOrder(userId: Int, totalProduct: Int, totalPrices: Int, name: String, address: String, phone: String,
                 send: Int, duration: Int, bank: String, rek: String, namerek: String,date: String, limitdate: String, statuspay: Int){
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(ORDER_USER_ID, userId)
        values.put(ORDER_TOTAL_PRODUCT, totalProduct)
        values.put(ORDER_TOTAL_PRICES, totalPrices)
        values.put(ORDER_NAME, name)
        values.put(ORDER_ADDRESS, address)
        values.put(ORDER_PHONE, phone)
        values.put(ORDER_SEND, send)
        values.put(ORDER_DURATION, duration)
        values.put(ORDER_BANK, bank)
        values.put(ORDER_REK, rek)
        values.put(ORDER_NAMEREK, namerek)
        values.put(ORDER_DATE, date)
        values.put(ORDER_LIMIT_DATE, limitdate)
        values.put(ORDER_STATUS_PAY, statuspay)
        db.insert(TABLE_ORDER, null, values)
    }

    fun addDetailOrder(orderId: Int, productId: Int, totalProduct: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DETAIL_ORDER_ORDER_ID, orderId)
        values.put(DETAIL_ORDER_PRODUCT_ID, productId)
        values.put(DETAIL_ORDER_TOTAL_PRODUCT, totalProduct)
        db.insert(TABLE_DETAIL_ORDER, null, values)
    }

    fun addProductSending(orderId: Int, image: ByteArray, date: String, status: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_SENDING_ORDER_ID, orderId)
        values.put(PRODUCT_SENDING_IMAGE, image)
        values.put(PRODUCT_SENDING_DATE, date)
        values.put(PRODUCT_SENDING_STATUS, status)
        db.insert(TABLE_PRODUCT_SENDING, null, values)
    }

    fun addMail(userId: Int, message: String, date: String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(MAIL_USER_ID, userId)
        values.put(MAIL_MESSAGE, message)
        values.put(MAIL_DATE, date)
        db.insert(TABLE_MAIL, null, values)
    }
    fun getorderid(userId: Int, date: String, limitdate: String): Int? {
        val db=this.readableDatabase
        val query= "Select " + ORDER_ID + " from " + TABLE_ORDER + " where " +
                ORDER_USER_ID + " = '" + userId + "' AND "+ ORDER_DATE + " = '" + date+"' AND "+ ORDER_LIMIT_DATE + " = '" + limitdate+"'"
        val c = db.rawQuery(query, null)
        var result: Int?=null
        if(c.moveToFirst()){
            do{
                result=c.getInt(c.getColumnIndex(ORDER_ID))
            }while (c.moveToNext())
        }
        return result
    }

    fun getNameProduct(id: Int): String? {
        val db = this.readableDatabase
        val query= "Select " + PRODUCT_NAME + " from " + TABLE_PRODUCT + " where " + PRODUCT_ID + " = '" + id + "'"
        val c = db.rawQuery(query, null)
        var result: String?=null
        if(c.moveToFirst()){
            do{
                result=c.getString(c.getColumnIndex(PRODUCT_NAME))
            }while (c.moveToNext())
        }
        return result
    }
    fun getPricesProduct(id: Int): Int? {
        val db = this.readableDatabase
        val query= "Select " + PRODUCT_PRICES+ " from " + TABLE_PRODUCT + " where " + PRODUCT_ID + " = '" + id + "'"
        val c = db.rawQuery(query, null)
        var result: Int?=null
        if(c.moveToFirst()){
            do{
                result=c.getInt(c.getColumnIndex(PRODUCT_PRICES))
            }while (c.moveToNext())
        }
        return result
    }
    fun getStockProduct(id: Int): Int? {
        val db = this.readableDatabase
        val query= "Select " + PRODUCT_STOCK+ " from " + TABLE_PRODUCT + " where " + PRODUCT_ID + " = '" + id + "'"
        val c = db.rawQuery(query, null)
        var result: Int?=null
        if(c.moveToFirst()){
            do{
                result=c.getInt(c.getColumnIndex(PRODUCT_STOCK))
            }while (c.moveToNext())
        }
        return result
    }

    fun getImageProduct(id: Int): ByteArray? {
        val db = this.readableDatabase

        val query= "Select " + PRODUCT_IMAGE + " from " + TABLE_PRODUCT + " where " + PRODUCT_ID + " = '" + id + "'"
        val c = db.rawQuery(query, null)
        var result: ByteArray?=null
        if(c.moveToFirst()){
            do{
                result=c.getBlob(c.getColumnIndex(PRODUCT_IMAGE))
            }while (c.moveToNext())
        }
        return result

    }

    fun getImagePay(id: Int): ByteArray? {
        val db = this.readableDatabase

        val query= "Select " + PRODUCT_SENDING_IMAGE + " from " + TABLE_PRODUCT_SENDING + " where " + PRODUCT_SENDING_ID + " = '" + id + "'"
        val c = db.rawQuery(query, null)
        var result: ByteArray?=null
        if(c.moveToFirst()){
            do{
                result=c.getBlob(c.getColumnIndex(PRODUCT_SENDING_IMAGE))
            }while (c.moveToNext())
        }
        return result

    }

    fun getinfoproduct(id: Int): MutableList<Basket>{
        var list: MutableList<Basket> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from "+ TABLE_BASKET + " where "+ BASKET_USER_ID + " = '" + id + "' and "+ BASKET_ISCHECKED + " = 1"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                var basket= Basket()
                basket.id=result.getInt(result.getColumnIndex(BASKET_ID))
                basket.userIds=result.getInt(result.getColumnIndex(BASKET_USER_ID))
                basket.productId=result.getInt(result.getColumnIndex(BASKET_PRODUCT_ID))
                basket.totalProduct=result.getInt(result.getColumnIndex(BASKET_TOTAL_PRODUCT))
                basket.isChecked=result.getInt(result.getColumnIndex(BASKET_ISCHECKED))

                list.add(basket)
            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }
    fun getprofileuser(id: Int): MutableList<User>{
        var list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from "+ TABLE_USER + " where "+ USER_ID + " = '" + id + "'"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                var user= User()
                user.id=result.getInt(result.getColumnIndex(USER_ID))
                user.name=result.getString(result.getColumnIndex(USER_NAME))
                user.email=result.getString(result.getColumnIndex(USER_EMAIL))
                user.password=result.getString(result.getColumnIndex(USER_PASSWORD))

                list.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
    fun getUserOrder(id: Int): MutableList<Order> {
        var list: MutableList<Order> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from "+ TABLE_ORDER + " where "+ ORDER_ID + " = '" + id + "' "
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                var order=Order()
                order.id=result.getInt(result.getColumnIndex(ORDER_ID))
                order.userId=result.getInt(result.getColumnIndex(ORDER_USER_ID))
                order.totalProduct=result.getInt(result.getColumnIndex(ORDER_TOTAL_PRODUCT))
                order.totalPrices=result.getInt(result.getColumnIndex(ORDER_TOTAL_PRICES))
                order.name=result.getString(result.getColumnIndex(ORDER_NAME))
                order.address=result.getString(result.getColumnIndex(ORDER_ADDRESS))
                order.phone=result.getString(result.getColumnIndex(ORDER_PHONE))
                order.send=result.getInt(result.getColumnIndex(ORDER_SEND))
                order.duration=result.getInt(result.getColumnIndex(ORDER_DURATION))
                order.bank=result.getString(result.getColumnIndex(ORDER_BANK))
                order.rek=result.getString(result.getColumnIndex(ORDER_REK))
                order.namerek=result.getString(result.getColumnIndex(ORDER_NAMEREK))
                order.date=result.getString(result.getColumnIndex(ORDER_DATE))
                order.limitdate=result.getString(result.getColumnIndex(ORDER_LIMIT_DATE))
                order.statuspay=result.getInt(result.getColumnIndex(ORDER_STATUS_PAY))
                list.add(order)
            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }

    fun getTotalPrices(id: Int): Int? {
        val db = this.readableDatabase
        val query= "Select sum(" + PRODUCT_PRICES + " * "+ BASKET_TOTAL_PRODUCT +") from " + TABLE_BASKET + " inner join "+
                TABLE_PRODUCT +" on "+ BASKET_PRODUCT_ID +" = "+ PRODUCT_ID +" where " + BASKET_USER_ID + " = '" + id + "' and "+ BASKET_ISCHECKED +" = 1"
        val c = db.rawQuery(query, null)
        var result: Int?=null
        if(c.moveToFirst()){
            do{
                result=c.getInt(0)
            }while (c.moveToNext())
        }
        return result
    }

    fun getTotalProduct(id: Int): Int? {
        val db = this.readableDatabase
        val query= "Select sum(" + BASKET_TOTAL_PRODUCT +") from " + TABLE_BASKET + " inner join "+
                TABLE_PRODUCT +" on "+ BASKET_PRODUCT_ID +" = "+ PRODUCT_ID +" where " + BASKET_USER_ID + " = '" + id + "' and "+ BASKET_ISCHECKED +" = 1"
        val c = db.rawQuery(query, null)
        var result: Int?=null
        if(c.moveToFirst()){
            do{
                result=c.getInt(0)
            }while (c.moveToNext())
        }
        return result
    }

    fun updateProduct(id: Int, name: String, prices: Int, stock: Int, image: ByteArray) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_NAME, name)
        values.put(PRODUCT_PRICES, prices)
        values.put(PRODUCT_STOCK, stock)
        values.put(PRODUCT_IMAGE, image)
        db.update(TABLE_PRODUCT, values, "$PRODUCT_ID = ?", arrayOf(id.toString()))
    }

    fun updateUser(id: Int, name: String, email: String, password: String) {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_NAME, name)
        values.put(USER_EMAIL, email)
        values.put(USER_PASSWORD, password)
        db.update(TABLE_USER, values, "$USER_ID=?", arrayOf(id.toString()))
    }

    fun updateTotalProduct(id: Int, totalProduct: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(BASKET_TOTAL_PRODUCT, totalProduct)
        db.update(TABLE_BASKET, values,"$BASKET_ID = ?", arrayOf(id.toString()))
    }

    fun updateIsChecked(id: Int, checked: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(BASKET_ISCHECKED, checked)
        db.update(TABLE_BASKET, values,"$BASKET_ID = ?", arrayOf(id.toString()))
    }

    fun updateStatusPay(id: Int, status: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ORDER_STATUS_PAY, status)
        db.update(TABLE_ORDER, values,"$ORDER_ID = ?", arrayOf(id.toString()))
    }

    fun updateSend(id: Int, date: String, status: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_SENDING_DATE, date)
        values.put(PRODUCT_SENDING_STATUS, status)
        db.update(TABLE_PRODUCT_SENDING, values,"$PRODUCT_SENDING_ID = ?", arrayOf(id.toString()))
    }

    fun deleteProduct(id : Int){
        val db = this.writableDatabase
        db.delete(TABLE_PRODUCT, "$PRODUCT_ID = ?", arrayOf(id.toString()))
    }
//    fun deleteDate(){
//        val db = this.writableDatabase
//        db.delete(TABLE_DATE,"$DATE_ID=*",)
//    }

    fun deleteBasket(id: Int){
        val db = this.writableDatabase
        db.delete(TABLE_BASKET, "$BASKET_ID= ?", arrayOf(id.toString()))
    }

    fun deleteOrderProduct(id: Int){
        val db = this.writableDatabase
        db.delete(TABLE_ORDER, "$BASKET_ID= ?", arrayOf(id.toString()))
    }

    fun logIn(email: String, password: String): Int? {
        val db=this.readableDatabase
        val query= "Select " + USER_ID + " from " + TABLE_USER + " where " + USER_EMAIL + " = '" + email + "' AND "+ USER_PASSWORD + " = '" + password+"'"
        val c = db.rawQuery(query, null)
        var result: Int?=null
        if(c.moveToFirst()){
            do{
                result=c.getInt(c.getColumnIndex(USER_ID))
            }while (c.moveToNext())
        }
        return result

    }
    fun allProducts(keyword: String): ArrayList<ProductModel> {
            val productModelArrayList = ArrayList<ProductModel>()

//            val selectQuery = "SELECT * FROM $TABLE_PRODUCT where "+ PRODUCT_NAME + " like '%"+keyword+"%'"
        val selectQuery = "SELECT * FROM $TABLE_PRODUCT where "+ PRODUCT_NAME + " like '%"+keyword+"%' or "+ PRODUCT_PRICES + " like '%"+keyword+"%'"

        val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val productModel = ProductModel()
                    productModel.setIds(c.getInt(c.getColumnIndex(PRODUCT_ID)))
                    productModel.setNames(c.getString(c.getColumnIndex(PRODUCT_NAME)))
                    productModel.setPrices(c.getInt(c.getColumnIndex(PRODUCT_PRICES)))
                    productModel.setStocks(c.getInt(c.getColumnIndex(PRODUCT_STOCK)))

                    productModelArrayList.add(productModel)
                } while (c.moveToNext())
            }
            return productModelArrayList
        }
    val allProducts: ArrayList<ProductModel>
        get(){
            val productModelArrayList = ArrayList<ProductModel>()

            val selectQuery = "SELECT * FROM $TABLE_PRODUCT"
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val productModel = ProductModel()
                    productModel.setIds(c.getInt(c.getColumnIndex(PRODUCT_ID)))
                    productModel.setNames(c.getString(c.getColumnIndex(PRODUCT_NAME)))
                    productModel.setPrices(c.getInt(c.getColumnIndex(PRODUCT_PRICES)))
                    productModel.setStocks(c.getInt(c.getColumnIndex(PRODUCT_STOCK)))

                    productModelArrayList.add(productModel)
                } while (c.moveToNext())
            }
            return productModelArrayList
        }
    val allBaskets: ArrayList<BasketModel>
        get(){
            val basketModelArrayList = ArrayList<BasketModel>()

            val selectQuery = "SELECT * FROM $TABLE_BASKET"
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val basketModel = BasketModel()
                    basketModel.setid(c.getInt(c.getColumnIndex(BASKET_ID)))
                    basketModel.setuserid(c.getInt(c.getColumnIndex(BASKET_USER_ID)))
                    basketModel.setproductid(c.getInt(c.getColumnIndex(BASKET_PRODUCT_ID)))
                    basketModel.settotalproduct(c.getInt(c.getColumnIndex(BASKET_TOTAL_PRODUCT)))
                    basketModel.setischecked(c.getInt(c.getColumnIndex(BASKET_ISCHECKED)))

                    basketModelArrayList.add(basketModel)
                } while (c.moveToNext())
            }
            return basketModelArrayList
        }
    val allOrders: ArrayList<OrderModel>
        get(){
            val orderModelArrayList = ArrayList<OrderModel>()

            val selectQuery = "SELECT * FROM $TABLE_ORDER"
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val orderModel = OrderModel()
                    orderModel.setIds(c.getInt(c.getColumnIndex(ORDER_ID)))
                    orderModel.setUserIds(c.getInt(c.getColumnIndex(ORDER_USER_ID)))
                    orderModel.setTotalProducts(c.getInt(c.getColumnIndex(ORDER_TOTAL_PRODUCT)))
                    orderModel.setTotalPricess(c.getInt(c.getColumnIndex(ORDER_TOTAL_PRICES)))
                    orderModel.setNames(c.getString(c.getColumnIndex(ORDER_NAME)))
                    orderModel.setaddress(c.getString(c.getColumnIndex(ORDER_ADDRESS)))
                    orderModel.setPhones(c.getString(c.getColumnIndex(ORDER_PHONE)))
                    orderModel.setSends(c.getInt(c.getColumnIndex(ORDER_SEND)))
                    orderModel.setDurations(c.getInt(c.getColumnIndex(ORDER_DURATION)))
                    orderModel.setBanks(c.getString(c.getColumnIndex(ORDER_BANK)))
                    orderModel.setReks(c.getString(c.getColumnIndex(ORDER_REK)))
                    orderModel.setNamereks(c.getString(c.getColumnIndex(ORDER_NAMEREK)))
                    orderModel.setDates(c.getString(c.getColumnIndex(ORDER_DATE)))
                    orderModel.setLimitdates(c.getString(c.getColumnIndex(ORDER_LIMIT_DATE)))
                    orderModel.setStatuspays(c.getInt(c.getColumnIndex(ORDER_STATUS_PAY)))
                    orderModelArrayList.add(orderModel)
                } while (c.moveToNext())
            }
            return orderModelArrayList
        }
    fun allOrders2(id: Int): ArrayList<OrderModel> {
        val orderModelArrayList = ArrayList<OrderModel>()

        val selectQuery = "SELECT * FROM $TABLE_ORDER where " + ORDER_USER_ID + " = '" + id +"' and "+ ORDER_STATUS_PAY +" = 1 "
        val db= this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if(c.moveToFirst()){
            do{
                val orderModel = OrderModel()
                orderModel.setIds(c.getInt(c.getColumnIndex(ORDER_ID)))
                orderModel.setUserIds(c.getInt(c.getColumnIndex(ORDER_USER_ID)))
                orderModel.setTotalProducts(c.getInt(c.getColumnIndex(ORDER_TOTAL_PRODUCT)))
                orderModel.setTotalPricess(c.getInt(c.getColumnIndex(ORDER_TOTAL_PRICES)))
                orderModel.setNames(c.getString(c.getColumnIndex(ORDER_NAME)))
                orderModel.setaddress(c.getString(c.getColumnIndex(ORDER_ADDRESS)))
                orderModel.setPhones(c.getString(c.getColumnIndex(ORDER_PHONE)))
                orderModel.setSends(c.getInt(c.getColumnIndex(ORDER_SEND)))
                orderModel.setDurations(c.getInt(c.getColumnIndex(ORDER_DURATION)))
                orderModel.setBanks(c.getString(c.getColumnIndex(ORDER_BANK)))
                orderModel.setReks(c.getString(c.getColumnIndex(ORDER_REK)))
                orderModel.setNamereks(c.getString(c.getColumnIndex(ORDER_NAMEREK)))
                orderModel.setDates(c.getString(c.getColumnIndex(ORDER_DATE)))
                orderModel.setLimitdates(c.getString(c.getColumnIndex(ORDER_LIMIT_DATE)))
                orderModel.setStatuspays(c.getInt(c.getColumnIndex(ORDER_STATUS_PAY)))
                orderModelArrayList.add(orderModel)
            } while (c.moveToNext())
        }
        return orderModelArrayList
    }
    val allDetailOrder: ArrayList<DetailOrderModel>
        get(){
            val detailOrderModelArrayList = ArrayList<DetailOrderModel>()

            val selectQuery = "SELECT * FROM $TABLE_DETAIL_ORDER"
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val detailOrderModel = DetailOrderModel()
                    detailOrderModel.setIds(c.getInt(c.getColumnIndex(DETAIL_ORDER_ID)))
                    detailOrderModel.setOrders(c.getInt(c.getColumnIndex(DETAIL_ORDER_ORDER_ID)))
                    detailOrderModel.setProductIds(c.getInt(c.getColumnIndex(DETAIL_ORDER_PRODUCT_ID)))
                    detailOrderModel.setTotalProducts(c.getInt(c.getColumnIndex(
                        DETAIL_ORDER_TOTAL_PRODUCT)))
                    detailOrderModelArrayList.add(detailOrderModel)
                } while (c.moveToNext())
            }
            return detailOrderModelArrayList
        }
    val allProductSending: ArrayList<ProductSendingModel>
        get(){
            val productSendingModelArrayList = ArrayList<ProductSendingModel>()

            val selectQuery = "SELECT * FROM $TABLE_PRODUCT_SENDING order by "+ PRODUCT_SENDING_ID+" desc"
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val productSendingModel = ProductSendingModel()
                    productSendingModel.setIds(c.getInt(c.getColumnIndex(PRODUCT_SENDING_ID)))
                    productSendingModel.setOrderIds(c.getInt(c.getColumnIndex(PRODUCT_SENDING_ORDER_ID)))
                    productSendingModel.setDates(c.getString(c.getColumnIndex(PRODUCT_SENDING_DATE)))
                    productSendingModel.setStatuss(c.getInt(c.getColumnIndex(PRODUCT_SENDING_STATUS)))

                    productSendingModelArrayList.add(productSendingModel)
                } while (c.moveToNext())
            }
            return productSendingModelArrayList
        }
    fun getDetailOrder(id: Int): ArrayList<DetailOrderModel>{
            val detailOrderModelArrayList = ArrayList<DetailOrderModel>()

            val selectQuery = "SELECT * FROM $TABLE_DETAIL_ORDER where "+ DETAIL_ORDER_ORDER_ID + " = '"+id +"'"
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val detailOrderModel = DetailOrderModel()
                    detailOrderModel.setIds(c.getInt(c.getColumnIndex(DETAIL_ORDER_ID)))
                    detailOrderModel.setOrders(c.getInt(c.getColumnIndex(DETAIL_ORDER_ORDER_ID)))
                    detailOrderModel.setProductIds(c.getInt(c.getColumnIndex(DETAIL_ORDER_PRODUCT_ID)))
                    detailOrderModel.setTotalProducts(c.getInt(c.getColumnIndex(
                        DETAIL_ORDER_TOTAL_PRODUCT)))
                    detailOrderModelArrayList.add(detailOrderModel)
                } while (c.moveToNext())
            }
            return detailOrderModelArrayList
        }
    fun getUserMail(id: Int): ArrayList<MailModel>{
        val mailModelArrayList = ArrayList<MailModel>()

        val selectQuery = "SELECT * FROM $TABLE_MAIL where "+ MAIL_USER_ID + " = '"+id +"' order by "+ MAIL_ID+" desc"
        val db= this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if(c.moveToFirst()){
            do{
                val mailModel = MailModel()
                mailModel.setIds(c.getInt(c.getColumnIndex(MAIL_ID)))
                mailModel.setUserIds(c.getInt(c.getColumnIndex(MAIL_USER_ID)))
                mailModel.setMessages(c.getString(c.getColumnIndex(MAIL_MESSAGE)))
                mailModel.setDates(c.getString(c.getColumnIndex(MAIL_DATE)))
                mailModelArrayList.add(mailModel)
            } while (c.moveToNext())
        }
        return mailModelArrayList
    }

    fun getBaskets(id: Int): ArrayList<BasketModel>{
            val basketModelArrayList = ArrayList<BasketModel>()

            val selectQuery = "SELECT * FROM $TABLE_BASKET where " + BASKET_USER_ID + " = '" + id +"'"
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val basketModel = BasketModel()
                    basketModel.setid(c.getInt(c.getColumnIndex(BASKET_ID)))
                    basketModel.setuserid(c.getInt(c.getColumnIndex(BASKET_USER_ID)))
                    basketModel.setproductid(c.getInt(c.getColumnIndex(BASKET_PRODUCT_ID)))
                    basketModel.settotalproduct(c.getInt(c.getColumnIndex(BASKET_TOTAL_PRODUCT)))
                    basketModel.setischecked(c.getInt(c.getColumnIndex(BASKET_ISCHECKED)))

                    basketModelArrayList.add(basketModel)
                } while (c.moveToNext())
            }
            return basketModelArrayList
        }

    fun allOrders(id: Int): ArrayList<OrderModel> {
            val orderModelArrayList = ArrayList<OrderModel>()

            val selectQuery = "SELECT * FROM $TABLE_ORDER where " + ORDER_USER_ID + " = '" + id +"' and "+ ORDER_STATUS_PAY +" = 0 "
            val db= this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do{
                    val orderModel = OrderModel()
                    orderModel.setIds(c.getInt(c.getColumnIndex(ORDER_ID)))
                    orderModel.setUserIds(c.getInt(c.getColumnIndex(ORDER_USER_ID)))
                    orderModel.setTotalProducts(c.getInt(c.getColumnIndex(ORDER_TOTAL_PRODUCT)))
                    orderModel.setTotalPricess(c.getInt(c.getColumnIndex(ORDER_TOTAL_PRICES)))
                    orderModel.setNames(c.getString(c.getColumnIndex(ORDER_NAME)))
                    orderModel.setaddress(c.getString(c.getColumnIndex(ORDER_ADDRESS)))
                    orderModel.setPhones(c.getString(c.getColumnIndex(ORDER_PHONE)))
                    orderModel.setSends(c.getInt(c.getColumnIndex(ORDER_SEND)))
                    orderModel.setDurations(c.getInt(c.getColumnIndex(ORDER_DURATION)))
                    orderModel.setBanks(c.getString(c.getColumnIndex(ORDER_BANK)))
                    orderModel.setReks(c.getString(c.getColumnIndex(ORDER_REK)))
                    orderModel.setNamereks(c.getString(c.getColumnIndex(ORDER_NAMEREK)))
                    orderModel.setDates(c.getString(c.getColumnIndex(ORDER_DATE)))
                    orderModel.setLimitdates(c.getString(c.getColumnIndex(ORDER_LIMIT_DATE)))
                    orderModel.setStatuspays(c.getInt(c.getColumnIndex(ORDER_STATUS_PAY)))
                    orderModelArrayList.add(orderModel)
                } while (c.moveToNext())
            }
            return orderModelArrayList
        }

    companion object {
        var DATABASE_NAME = "petshop"
        private val DATABASE_VERSION = 1
        private val TABLE_USER = "Users"
        private val TABLE_PRODUCT="Products"
        private val TABLE_BASKET="Baskets"
        private val TABLE_ORDER="Orders"
        private val TABLE_DETAIL_ORDER="Detail_order"
        private val TABLE_PRODUCT_SENDING="Product_sending"
        private val TABLE_MAIL="Mail"
        private val USER_ID="id_user"
        private val USER_NAME="name_user"
        private val USER_EMAIL="email"
        private val USER_PASSWORD="password"
        private val PRODUCT_ID="id_product"
        private val PRODUCT_NAME="name_product"
        private val PRODUCT_PRICES="prices_product"
        private val PRODUCT_STOCK="stock_product"
        private val PRODUCT_IMAGE = "image_product"
        private val BASKET_ID ="id_basket"
        private val BASKET_USER_ID ="basket_userId"
        private val BASKET_PRODUCT_ID ="basket_productId"
        private val BASKET_TOTAL_PRODUCT ="basket_totalProduct"
        private val BASKET_ISCHECKED ="basket_isChecked"
        private val ORDER_ID="id_order"
        private val ORDER_USER_ID="order_userId"
        private val ORDER_TOTAL_PRODUCT="order_totalProduct"
        private val ORDER_TOTAL_PRICES="order_totalPrices"
        private val ORDER_NAME="order_name"
        private val ORDER_ADDRESS="order_address"
        private val ORDER_PHONE="order_phone"
        private val ORDER_SEND="order_send"
        private val ORDER_DURATION="order_duration"
        private val ORDER_BANK="order_bank"
        private val ORDER_REK="order_rek"
        private val ORDER_NAMEREK="order_namerek"
        private val ORDER_DATE="order_date"
        private val ORDER_LIMIT_DATE="order_limit_date"
        private val ORDER_STATUS_PAY="order_status_pay"
        private val DETAIL_ORDER_ID="detail_order_id"
        private val DETAIL_ORDER_ORDER_ID="detail_order_orderId"
        private val DETAIL_ORDER_PRODUCT_ID="detail_order_productId"
        private val DETAIL_ORDER_TOTAL_PRODUCT="detail_order_totalProduct"
        private val PRODUCT_SENDING_ID="product_sending_id"
        private val PRODUCT_SENDING_ORDER_ID="product_sending_order_id"
        private val PRODUCT_SENDING_IMAGE="product_sending_image"
        private val PRODUCT_SENDING_DATE="product_sending_date"
        private val PRODUCT_SENDING_STATUS="product_sending_status"
        private val MAIL_ID="mail_id"
        private val MAIL_USER_ID="mail_userId"
        private val MAIL_MESSAGE="mail_message"
        private val MAIL_DATE="mail_date"

        private val CREATE_TABLE_USER = ("CREATE TABLE "
                + TABLE_USER + "(" + USER_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " VARCHAR(256)," + USER_EMAIL + " VARCHAR(256)," + USER_PASSWORD + " VARCHAR(256));")
        private val CREATE_TABLE_PRODUCT = ("CREATE TABLE "
                + TABLE_PRODUCT + "(" + PRODUCT_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + PRODUCT_NAME + " VARCHAR(256)," + PRODUCT_PRICES
                + " INTEGER," + PRODUCT_STOCK + " INTEGER, "+ PRODUCT_IMAGE +" BLOB);")
        private val CREATE_TABLE_BASKET = ("CREATE TABLE "
                + TABLE_BASKET + "(" + BASKET_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + BASKET_USER_ID + " INTEGER," + BASKET_PRODUCT_ID
                + " INTEGER," + BASKET_TOTAL_PRODUCT + " INTEGER, "+ BASKET_ISCHECKED +" INTEGER);")
        private val CREATE_TABLE_ORDER = ("CREATE TABLE "
                + TABLE_ORDER + "(" + ORDER_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ORDER_USER_ID + " INTEGER," + ORDER_TOTAL_PRODUCT
                + " INTEGER," + ORDER_TOTAL_PRICES + " INTEGER, "+ ORDER_NAME +" VARCHAR(256)," + ORDER_ADDRESS
                + " VARCHAR(256)," + ORDER_PHONE + " VARCHAR(256)," + ORDER_SEND + " INTEGER," + ORDER_DURATION
                + " INTEGER," + ORDER_BANK + " VARCHAR(256)," + ORDER_REK + " VARCHAR(256)," + ORDER_NAMEREK
                + " VARCHAR(256)," + ORDER_DATE + " VARCHAR(256)," + ORDER_LIMIT_DATE + " VARCHAR(256)," + ORDER_STATUS_PAY + " INTEGER);")
        private val CREATE_TABLE_DETAIL_ORDER = ("CREATE TABLE "
                + TABLE_DETAIL_ORDER + "(" + DETAIL_ORDER_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + DETAIL_ORDER_ORDER_ID + " INTEGER," + DETAIL_ORDER_PRODUCT_ID
                +" INTEGER," + DETAIL_ORDER_TOTAL_PRODUCT +" INTEGER);")
        private val CREATE_TABLE_PRODUCT_SENDING = ("CREATE TABLE "
                + TABLE_PRODUCT_SENDING + "(" + PRODUCT_SENDING_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + PRODUCT_SENDING_ORDER_ID + " INTEGER," + PRODUCT_SENDING_IMAGE
                +" BLOB," + PRODUCT_SENDING_DATE +" VARCHAR(256)," + PRODUCT_SENDING_STATUS +" INTEGER);")
        private val CREATE_TABLE_MAIL = ("CREATE TABLE "
                + TABLE_MAIL + "(" + MAIL_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + MAIL_USER_ID+ " INTEGER," + MAIL_MESSAGE
                +" VARCHAR(256)," + MAIL_DATE +" VARCHAR(256));")
    }
}