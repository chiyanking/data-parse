package nc.liat6.data.parser.bean;

/**
 * 数据块中的某一个格子
 * 
 * @author 6tail
 *
 */
public class Item{
  /** 该格子位于块中第几行，从0开始计 */
  private int row;
  /** 该格子位于块中第几列，从0开始计 */
  private int col;
  /** 格子的名称，由开发者自定义，并在数据解析后通过该名称获取，同一个块中的名称不允许重复 */
  private String name;
  /** 格子的内容 */
  private String content;
  /** 格子的默认数据类型 */
  public static final ItemType DEFAULT_TYPE = ItemType.string;
  /** 格子的数据类型 */
  private ItemType type = DEFAULT_TYPE;

  public Item(){}

  public Item(String name,String content){
    setName(name);
    setContent(content);
  }

  public Item(int row,int col,String content){
    setRow(row);
    setCol(col);
    setContent(content);
  }

  public String toString(){
    StringBuilder s = new StringBuilder();
    s.append("{");
    s.append(row);
    s.append(",");
    s.append(col);
    s.append(" ");
    s.append(name);
    s.append("=");
    s.append(content);
    s.append("}");
    return s.toString();
  }

  public int getRow(){
    return row;
  }

  public void setRow(int row){
    this.row = row;
  }

  public int getCol(){
    return col;
  }

  public void setCol(int col){
    this.col = col;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }

  public ItemType getType(){
    return type;
  }

  public void setType(ItemType type){
    this.type = type;
  }
}