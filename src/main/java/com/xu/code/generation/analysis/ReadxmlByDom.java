package com.xu.code.generation.analysis;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * 用DOM方式读取xml文件
 * @author lune
 */
public class ReadxmlByDom {

    private String basePath;
    //包名
    @Value("${analysis.pack}")
    private static  String pack;
    //输出路径
    @Value(("${file.output.path}"))
    private static String outPutPath;
    //项目相对路径
    private static String path = "com.xu.code.generation";

    //实体名
    private static String entityName="UserDO";

    public ReadxmlByDom(String path){
        this.basePath = path;
    }

    private static DocumentBuilderFactory dbFactory = null;
    private static DocumentBuilder db = null;
    private static Document document = null;
    static{
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            db = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void getXML(String fileName) throws Exception{
        //将给定 URI 的内容解析为一个 XML 文档,并返回Document对象
        document = db.parse(fileName);
        //按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList
        NodeList bookList = document.getElementsByTagName("mapper");
        //遍历books
        for(int i=0;i<bookList.getLength();i++){
            //获取第i个book结点
            org.w3c.dom.Node node = bookList.item(i);

            node(node);


            //获取book结点的子节点,包含了Test类型的换行
            NodeList cList = node.getChildNodes();//System.out.println(cList.getLength());9

            //将一个book里面的属性加入数组
            ArrayList<String> contents = new ArrayList<>();
            for(int j=1;j<cList.getLength();j+=2){

                org.w3c.dom.Node cNode = cList.item(j);
                String content = cNode.getFirstChild().getTextContent();
                contents.add(content);
            }

        }

    }

    /**
     * 解析mapper节点以及mapper节点下的内容
     * @param node 节点
     */
    public void node(org.w3c.dom.Node node){
        //获取第i个book的所有属性
        NamedNodeMap namedNodeMap = node.getAttributes();
        //判断是什么标签
        switch (node.getNodeName()){
            case "select":

                break;
            case "insert":
                break;
            case "update":
                break;
            case "delete":
                break;
            case "mapper":
                String namespace = namedNodeMap.getNamedItem("namespace").getTextContent();
                analyticAttribute(namespace);
                break;
        }
    }

    /**
     *  解析标签属性
     * @param namespace 需要解析的属性值
     * @return
     */
    public String analyticAttribute(String namespace){
        final String OPEN = "${";
        final String CLOSE = "}";

        String newString = "";
        String string = "";
        if (namespace != null ) {
            int start = namespace.indexOf(OPEN);
            int end = namespace.indexOf(CLOSE);

            while (start > -1 && end > start) {
                String prepend = namespace.substring(0, start);
                String append = namespace.substring(end + CLOSE.length());
                String propName = namespace.substring(start + OPEN.length(), end);

                if(propName.equals("entityName")){
                    namespace = prepend+entityName+append;
                }else if(propName.equals("entityPackage")){
                    namespace = prepend+pack+append;
                }else if(propName.equals("bussiPackage")){
                    namespace = prepend+path+append;
                }

                start = namespace.indexOf(OPEN);
                end = namespace.indexOf(CLOSE);
            }
        }
        return namespace;
    }


}

