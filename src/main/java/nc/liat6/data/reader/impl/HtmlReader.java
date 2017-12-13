package nc.liat6.data.reader.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.data.reader.AbstractReader;
import nc.liat6.data.reader.bean.Source;
import nc.liat6.data.util.ReaderHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * HTML直接改后缀为xls或xlsx的文件读取
 * 
 * @author 6tail
 *
 */
public class HtmlReader extends AbstractReader{
  public static final int DOC_TYPE_UNKNOWN = 0;
  public static final int DOC_TYPE_HTML = 1;
  public static final int DOC_TYPE_XML = 2;
  protected Document doc;
  protected Elements trs;
  protected int rowCount;
  /**列数*/
  protected int colCount;
  protected int rowReaded;
  protected int docType;
  protected Map<Integer,Integer> rowspans = new HashMap<Integer,Integer>();

  public HtmlReader(Source source){
    super(source);
  }

  public void load() throws IOException{
    switch(source.getSourceType()){
      case file:
        doc = Jsoup.parse(source.getFile(),ReaderHelper.getCharset(source.getFile()));
        break;
      case inputStream:
        doc = Jsoup.parse(source.getInputStream(),ReaderHelper.getCharset(source.getInputStream()),null);
        break;
    }
    stop = false;
    rowCount = 0;
    colCount = 0;
    rowReaded = 0;
    rowspans.clear();
    docType = DOC_TYPE_UNKNOWN;
    trs = doc.getElementsByTag("tr");
    if(trs.size()>0){
      docType = DOC_TYPE_HTML;
    }else{
      trs = doc.getElementsByTag("row");
      if(trs.size()>0){
        docType = DOC_TYPE_XML;
      }
    }
    rowCount = trs.size();
  }

  public List<String> nextLine(){
    if(stop) return null;
    if(rowReaded>=rowCount) return null;
    Element tr = trs.get(rowReaded);
    rowReaded++;
    List<String> line = new ArrayList<String>();
    Elements tds = tr.children();
    if(rowReaded==1){
      colCount = tds.size();
    }
    int colIndex = 0;
    for(int i=0;i<colCount;i++){
      Integer restRow = rowspans.get(i);
      if(null==restRow||restRow<1){
        Element n = tds.get(colIndex++);
        line.add(n.text().trim());
        int rowspan = 0;
        int colspan = 0;
        String attrRowspan = n.attr("rowspan");
        if(attrRowspan.length()>0){
          rowspan = Integer.parseInt(attrRowspan)-1;
          rowspans.put(i,rowspan);
        }
        String attrColspan = n.attr("colspan");
        if(attrColspan.length()>0){
          colspan = Integer.parseInt(attrColspan)-1;
          if(rowReaded==1){
            colCount += colspan;
          }
          for(int x=0;x<colspan;x++){
            rowspans.put(i+x+1,rowspan+1);
          }
        }
      }else{
        restRow--;
        rowspans.put(i,restRow);
        line.add("");
      }
    }
    return line;
  }
}