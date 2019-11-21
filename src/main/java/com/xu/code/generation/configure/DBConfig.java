package com.xu.code.generation.configure;

import com.xu.code.generation.entity.TableDo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConfig {

    public void connect(String username,String password,String url,String diver){
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(diver);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(url,username,password);

            DatabaseMetaData dbMetaData = conn.getMetaData();

            String catalog = conn.getCatalog(); //catalog 其实也就是数据库名
            ResultSet tablesResultSet = dbMetaData.getTables(catalog,null,null,new String[]{"TABLE"});

            TableDo tableDo = new TableDo();

            while(tablesResultSet.next()){
                String tableName = tablesResultSet.getString("TABLE_NAME");
                tableDo.getTableName().add(tableName);
            }

            field(tableDo,dbMetaData);

            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    /**
     *  获取表字段
     * @param tableDo 表集合实体
     * @param dbMetaData 数据库连接
     */
    public void field(TableDo tableDo,DatabaseMetaData dbMetaData){
        String columnName;
        String columnType;
        for (String tableNanme:tableDo.getTableName()) {
            ResultSet colRet = null;
            try {
                colRet = dbMetaData.getColumns(null,"%", tableNanme,"%");
                while(colRet.next()) {
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                int datasize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
                System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+ nullable);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     *  字段转属性
     * @param field 数据库字段
     */
    public void fieldsToAttribute(String field){

    }
}
