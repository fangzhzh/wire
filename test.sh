#!/bin/sh
WIREHOME=~/workspace/wire
PACKAGE_NAME=com.android.test
PACKAGE_DIR=out/com/android/test
TARGET_DIR=$HOME/testApp/Android/app/src/main/java/com.android.test
## generating files
[ -d $WIREHOME/out ] && rm -r $WIREHOME/out
mkdir $WIREHOME/out
java  -jar $WIREHOME/wire-compiler/target/garena-wire-compiler-2.0.0-SNAPSHOT-jar-with-dependencies.jar --proto_path=$WIREHOME/protos-repo --java_out=$WIREHOME/out  example_cmd.proto
## delete useless code
sed -i ''  '/Code generated/d' $PACKAGE_DIR/*.java
sed -i ''  '/Source file/d' $PACKAGE_DIR/*.java

## replace string for DB files 

sed -i ''  's#@DatabaseTable#/** \
 * TODO: id = true  \
 */ \
@DatabaseTable#' $PACKAGE_DIR/DB*.java
sed -i ''  's/com.squareup.wire.DatabaseField/com.j256.ormlite.field.DatabaseField/' $PACKAGE_DIR/DB*.java
sed -i ''  's/private ByteString/private byte[]/' $PACKAGE_DIR/DB*.java
sed -i ''  's/final ByteString/final byte[]/' $PACKAGE_DIR/DB*.java
sed -i ''  's/(ByteString/(byte[]/' $PACKAGE_DIR/DB*.java
sed -i ''  's/import okio.ByteString;//' $PACKAGE_DIR/DB*.java
### appended lost import
sed -i ''  's/com.squareup.wire.DatabaseTable;/com.j256.ormlite.table.DatabaseTable;' $PACKAGE_DIR/DB*.java
### delete code  
sed -i ''  '/Code generated/d' $PACKAGE_DIR/DB*.java
sed -i ''  '/Source file/d' $PACKAGE_DIR/DB*.java
cp -v $PACKAGE_DIR/DB*.java $TARGET_DIR/database/orm/
## replace string for Dao files 
sed -i ''  's/import\ DatabaseHelper/import com.garena.android.appkit.database.DatabaseHelper/' $PACKAGE_DIR/*Dao.java
sed -i ''  's/$PACKAGE_NAME.database.orm.bean;/$PACKAGE_NAME.database.orm.dao;/' $PACKAGE_DIR/*Dao.java
### appended lost import
sed -i ''  's/com.squareup.wire.BaseInfoDao;/com.garena.android.appkit.database.dao.BaseInfoDao; \
import com.j256.ormlite.dao.Dao; \
import com.garena.android.appkit.logging.BBAppLogger; \
import com.j256.ormlite.stmt.QueryBuilder; \
import java.util.ArrayList; \
import java.util.concurrent.Callable;/' $PACKAGE_DIR/*Dao.java
sed -i ''  's/import\ DB/import $PACKAGE_NAME.database.orm.bean.DB/' $PACKAGE_DIR/*Dao.java
### delete code  
sed -i ''  '/Code generated/d' $PACKAGE_DIR/*Dao.java
sed -i ''  '/Source file/d' $PACKAGE_DIR/*Dao.java
cp -v $PACKAGE_DIR/*Dao.java $TARGET_DIR/database/orm/
## replace string for VM files 
sed -i ''  's/$PACKAGE_NAME.database.orm.bean/$PACKAGE_NAME.data.viewmodel/' $PACKAGE_DIR/VM*.java
sed -i ''  's/private ByteString/private byte[]/' $PACKAGE_DIR/VM*.java
sed -i ''  's/final ByteString/final byte[]/' $PACKAGE_DIR/VM*.java
sed -i ''  's/(ByteString/(byte[]/' $PACKAGE_DIR/VM*.java
### appended lost import
#sed -i ''  's/$PACKAGE_NAME.data.viewmodel;/$PACKAGE_NAME.data.viewmodel; \
#import java.lang.String;/' $PACKAGE_DIR/VM*.java
sed -i ''  's/import\ DB/import $PACKAGE_NAME.database.orm.bean.DB/' $PACKAGE_DIR/VM*.java
### delete code  
sed -i ''  '/Code generated/d' $PACKAGE_DIR/VM*.java
sed -i ''  '/Source file/d' $PACKAGE_DIR/VM*.java
cp -v $PACKAGE_DIR/VM*.java $TARGET_DIR/data/viewmodel

## replace store 
sed -i ''  '/import/d' $PACKAGE_DIR/*Store.java
sed -i ''  's/$PACKAGE_NAME.database.orm.bean;/$PACKAGE_NAME.data.store;/' $PACKAGE_DIR/*Store.java
cp -v $PACKAGE_DIR/*Store.java $TARGET_DIR/data/store
