package sample;

import java.io.File;
import java.io.IOException;
import nc.liat6.data.parser.IParser;
import nc.liat6.data.parser.ParserFactory;
import nc.liat6.data.parser.bean.Block;
import nc.liat6.data.parser.bean.Item;
import nc.liat6.data.parser.rule.IParserRule;
import nc.liat6.data.parser.rule.ParserRuleSingleLine;
import org.junit.Test;

/**
 * 最简单的doc文件解析示例
 * @author 6tail
 *
 */
public class MostSimpleDocSample{

  @Test
  public  void all() throws IOException{
    //xls格式
    File file = new File("files\\最简单的.doc");
    //File file = new File("files\\最简单的.docx");

    //使用列对应字母，数据从第1行开始（行从0开始计）
    IParserRule rule = new ParserRuleSingleLine(0){};

    IParser parser = ParserFactory.getInstance().getParser(file,rule);

    Block block = null;
    while(null!=(block = parser.nextBlock())){
      for(Item item:block.getItems()){
        System.out.print(item.getContent());
        System.out.print("\t");
      }
      System.out.println();
      System.out.println("_____________________________________");
    }
  }

}