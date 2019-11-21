package com.xu.code.generation;



import com.xu.code.generation.analysis.ReadxmlByDom;
import com.xu.code.generation.configure.Constant;
import com.xu.code.generation.configure.DBConfig;

import java.io.File;

public class Main {

    public static void main(String[] args){
//        String path = Constant.OUTPUT_PATH+Constant.ANALYSIS_PACK+Constant.MAPPER_PACK_NAME;
//        File file = new File(path);
//        file.mkdirs();
//        ReadxmlByDom readxmlByDom = new ReadxmlByDom(path);

        DBConfig dbConfig = new DBConfig();
        dbConfig.connect(Constant.DB_USERNAME,Constant.DB_PASSWORD,Constant.DB_URL,Constant.JDBC_DRIVER);
    }
}
