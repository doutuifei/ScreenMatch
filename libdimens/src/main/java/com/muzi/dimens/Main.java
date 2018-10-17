package com.muzi.dimens;

import org.xml.sax.helpers.AttributesImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

public class Main {
    private final static String fileName = "./libdimens/src/main/res/values/dimens.xml";
    private final static float dpStepSize = 0.5f;
    private final static float DP_FROM = -120f;
    private final static float DP_TO = 1204f;
    private final static float spStepSize = 1f;
    private final static int SP_FROM = 9;
    private final static int SP_TO = 30;

    public static void main(String[] args) {
        List<DimenItem> items = new ArrayList<>();
        items.addAll(cerateDpData());
        items.addAll(cerateSpData());
        createDestinationDimens(items, fileName);
    }

    private static List<DimenItem> cerateDpData() {
        List<DimenItem> items = new ArrayList<>();
        float dp = DP_FROM;
        String dpName;
        while (dp >= DP_FROM && dp <= DP_TO) {
            if (dp == 0) {
                dp += dpStepSize;
                continue;
            }
            if (dp > 0) {
                dpName = String.valueOf(dp).replace(".", "_");
            } else {
                dpName = "m_" + String.valueOf(Math.abs(dp)).replace(".", "_");
            }
            if (dpName.endsWith("_0")) {
                dpName = dpName.substring(0, dpName.length() - 2);
            }
            DimenItem dimenItem = new DimenItem();
            dimenItem.name = "dp_" + dpName;
            dimenItem.value = dp + "dp";
            System.out.println(dimenItem);
            items.add(dimenItem);
            dp += dpStepSize;
        }
        return items;
    }

    private static List<DimenItem> cerateSpData() {
        List<DimenItem> items = new ArrayList<>();
        float sp = SP_FROM;
        String spName;
        while (sp >= SP_FROM && sp <= SP_TO) {
            spName = String.valueOf(sp).replace(".", "_");
            if (spName.endsWith("_0")) {
                spName = spName.substring(0, spName.length() - 2);
            }
            DimenItem dimenItem = new DimenItem();
            dimenItem.name = "sp_" + spName;
            dimenItem.value = sp + "sp";
            System.out.println(dimenItem);
            items.add(dimenItem);
            sp += spStepSize;
        }
        return items;
    }

    private static void createDestinationDimens(List<DimenItem> list, String outPutFile) {
        try {
            File targetFile = new File(outPutFile);
            if (targetFile.exists()) {
                try {
                    targetFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //创建SAXTransformerFactory实例
            SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            //创建TransformerHandler实例
            TransformerHandler handler = saxTransformerFactory.newTransformerHandler();
            //创建Transformer实例
            Transformer transformer = handler.getTransformer();
            //是否自动添加额外的空白
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //设置字符编码
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //添加xml版本，默认也是1.0
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            //保存xml路径
            StreamResult result = new StreamResult(targetFile);
            handler.setResult(result);
            //创建属性Attribute对象
            AttributesImpl attributes = new AttributesImpl();
            attributes.clear();
            //开始xml
            handler.startDocument();
            //换行
            handler.characters("\n".toCharArray(), 0, "\n".length());
            //写入根节点resources
            handler.startElement("", "", SAXReadHandler.ELEMENT_RESOURCE, attributes);
            //集合大小
            int size = list.size();
            for (int i = 0; i < size; i++) {
                DimenItem dimenBean = list.get(i);
                String value = dimenBean.value;
                attributes.clear();
                attributes.addAttribute("", "", SAXReadHandler.PROPERTY_NAME, "", dimenBean.name);

                //新dimen之前，换行、缩进
                handler.characters("\n".toCharArray(), 0, "\n".length());
                handler.characters("\t".toCharArray(), 0, "\t".length());

                //开始标签对输出
                handler.startElement("", "", SAXReadHandler.ELEMENT_DIMEN, attributes);
                handler.characters(value.toCharArray(), 0, value.length());
                handler.endElement("", "", SAXReadHandler.ELEMENT_DIMEN);
            }
            handler.endElement("", "", SAXReadHandler.ELEMENT_RESOURCE);
            handler.endDocument();
            System.out.println(">>>>> " + outPutFile + " 文件生成完成!");
        } catch (Exception e) {
            System.out.println("WARNING: " + outPutFile + " 文件生成失败!");
            e.printStackTrace();
        }
    }

}
