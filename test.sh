#!/bin/sh
WIREHOME=~/workspace/wire
## generating files
rm -r $WIREHOME/out
java  -jar $WIREHOME/wire-compiler/target/garena-wire-compiler-2.0.0-SNAPSHOT-jar-with-dependencies.jar --proto_path=$WIREHOME/protos-repo --java_out=$WIREHOME/out  beeshop_cmd.proto
## delete useless code
sed -i ''  '/Code generated/d' out/com/shopee/app/database/orm/bean/*.java
sed -i ''  '/Source file/d' out/com/shopee/app/database/orm/bean/*.java

## replace string for DB files 

sed -i ''  's#@DatabaseTable#/** \
 * TODO: id = true  \
 */ \
@DatabaseTable#' out/com/shopee/app/database/orm/bean/DB*.java
sed -i ''  's/com.squareup.wire.DatabaseField/com.j256.ormlite.field.DatabaseField/' out/com/shopee/app/database/orm/bean/DB*.java
sed -i ''  's/private ByteString/private byte[]/' out/com/shopee/app/database/orm/bean/DB*.java
sed -i ''  's/final ByteString/final byte[]/' out/com/shopee/app/database/orm/bean/DB*.java
sed -i ''  's/(ByteString/(byte[]/' out/com/shopee/app/database/orm/bean/DB*.java
sed -i ''  's/import okio.ByteString;//' out/com/shopee/app/database/orm/bean/DB*.java
### appended lost import
sed -i ''  's/com.squareup.wire.DatabaseTable;/com.j256.ormlite.table.DatabaseTable; \
import com.shopee.app.domain.data.DataMapper;/' out/com/shopee/app/database/orm/bean/DB*.java
### delete code  
sed -i ''  '/Code generated/d' out/com/shopee/app/database/orm/bean/DB*.java
sed -i ''  '/Source file/d' out/com/shopee/app/database/orm/bean/DB*.java
cp out/com/shopee/app/database/orm/bean/DB*.java ~/android/shopee/app/src/main/java/com/shopee/app/database/orm/bean/
## replace string for Dao files 
sed -i ''  's/import\ DatabaseHelper/import com.garena.android.appkit.database.DatabaseHelper/' out/com/shopee/app/database/orm/bean/*Dao.java
sed -i ''  's/com.shopee.app.database.orm.bean;/com.shopee.app.database.orm.dao;/' out/com/shopee/app/database/orm/bean/*Dao.java
### appended lost import
sed -i ''  's/com.squareup.wire.BaseInfoDao;/com.garena.android.appkit.database.dao.BaseInfoDao; \
import com.j256.ormlite.dao.Dao; \
import com.garena.android.appkit.logging.BBAppLogger; \
import com.j256.ormlite.stmt.QueryBuilder; \
import java.util.ArrayList; \
import java.util.concurrent.Callable;/' out/com/shopee/app/database/orm/bean/*Dao.java
sed -i ''  's/import\ DB/import com.shopee.app.database.orm.bean.DB/' out/com/shopee/app/database/orm/bean/*Dao.java
### delete code  
sed -i ''  '/Code generated/d' out/com/shopee/app/database/orm/bean/*Dao.java
sed -i ''  '/Source file/d' out/com/shopee/app/database/orm/bean/*Dao.java
cp out/com/shopee/app/database/orm/bean/*Dao.java ~/android/shopee/app/src/main/java/com/shopee/app/database/orm/dao/
## replace string for VM files 
sed -i ''  's/com.shopee.app.database.orm.bean/com.shopee.app.data.viewmodel/' out/com/shopee/app/database/orm/bean/VM*.java
sed -i ''  's/private ByteString/private byte[]/' out/com/shopee/app/database/orm/bean/VM*.java
sed -i ''  's/final ByteString/final byte[]/' out/com/shopee/app/database/orm/bean/VM*.java
sed -i ''  's/(ByteString/(byte[]/' out/com/shopee/app/database/orm/bean/VM*.java
### appended lost import
#sed -i ''  's/com.shopee.app.data.viewmodel;/com.shopee.app.data.viewmodel; \
#import java.lang.String;/' out/com/shopee/app/database/orm/bean/VM*.java
sed -i ''  's/import\ DB/import com.shopee.app.database.orm.bean.DB/' out/com/shopee/app/database/orm/bean/VM*.java
### delete code  
sed -i ''  '/Code generated/d' out/com/shopee/app/database/orm/bean/VM*.java
sed -i ''  '/Source file/d' out/com/shopee/app/database/orm/bean/VM*.java
cp out/com/shopee/app/database/orm/bean/VM*.java ~/android/shopee/app/src/main/java/com/shopee/app/data/viewmodel

## replace store 
sed -i ''  '/import/d' out/com/shopee/app/database/orm/bean/*Store.java
sed -i ''  's/com.shopee.app.database.orm.bean;/com.shopee.app.data.store;/' out/com/shopee/app/database/orm/bean/*Store.java
cp out/com/shopee/app/database/orm/bean/*Store.java ~/android/shopee/app/src/main/java/com/shopee/app/data/store
