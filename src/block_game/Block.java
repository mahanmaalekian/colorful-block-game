package block_game;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Block {
 private int xCoord;
 private int yCoord;
 private int size; // height/width of the square
 private int level; // the root (outer most block) is at level 0
 private int maxDepth; 
 private Color color;

 private Block[] children; // {UR, UL, LL, LR}

 public static Random gen = new Random(4);

 public static void main(String[] args) {
  Block blockDepth2 = new Block(0,4);
  blockDepth2.printColoredBlock();
  //blockDepth2.updateSizeAndPosition(16,0,0);
  //Block copy = blockDepth2.deepCopy();
  //copy.updateSizeAndPosition(8,0,0);
  //blockDepth2.printBlock();
  //copy.printBlock();
  //Block blockDepth3 = new Block(0,3);
  //blockDepth3.updateSizeAndPosition(32,0,0);
  //blockDepth3.printColoredBlock();
  //Block b1 = blockDepth3.getSelectedBlock(2,15,1);
  //b1.printBlock();
  //b1.reflect(1);
  //b1.printBlock();

//     Block blockDepth2 = new Block(0,2);
//     blockDepth2.updateSizeAndPosition(16,0,0);
//    blockDepth2.printColoredBlock();
     //gen.setSeed(2);
//     Block block = new Block(0, 2);
//     //block.updateSizeAndPosition(16,0,0);
//     block.printColoredBlock();
//     block.printBlock();
 }
 
 /*
  * These two constructors are here for testing purposes. 
  */
 public Block() {}
 
 public Block(int x, int y, int size, int lvl, int  maxD, Color c, Block[] subBlocks) {
  this.xCoord=x;
  this.yCoord=y;
  this.size=size;
  this.level=lvl;
  this.maxDepth = maxD;
  this.color=c;
  this.children = subBlocks;
 }
 
 

 /*
  * Creates a random block given its level and a max depth. 
  * 
  * xCoord, yCoord, size, and highlighted should not be initialized
  * (i.e. they will all be initialized by default)
  */
 public Block(int lvl, int maxDepth) {
  this.level = lvl;
  this.maxDepth = maxDepth;
  if (lvl < maxDepth && gen.nextDouble() < Math.exp(-0.25 * lvl)){
     this.children = new Block[4];
     color = null;
     children[0] = new Block(lvl+1, maxDepth);
     children[1] = new Block(lvl+1, maxDepth);
     children[2] = new Block(lvl+1, maxDepth);
     children[3] = new Block(lvl+1, maxDepth);
  }
  else {
      color = GameColors.BLOCK_COLORS[gen.nextInt(4)];
      children = new Block[0];
  }
 }


 /*
  * Updates size and position for the block and all of its sub-blocks, while
  * ensuring consistency between the attributes and the relationship of the 
  * blocks. 
  * 
  *  The size is the height and width of the block. (xCoord, yCoord) are the 
  *  coordinates of the top left corner of the block. 
  */
 public void updateSizeAndPosition (int size, int xCoord, int yCoord) {

      if (size < 0 || size % Math.pow(2, maxDepth-level) != 0){
       throw new IllegalArgumentException("Input size is invalid");
      }

      this.size = size;
      this.xCoord = xCoord;
      this.yCoord = yCoord;
      //block is a leaf
      if (children.length == 0) return;

      children[0].updateSizeAndPosition(size/2, xCoord+size/2, yCoord);
      children[1].updateSizeAndPosition(size/2, xCoord, yCoord);
      children[2].updateSizeAndPosition(size/2, xCoord, yCoord+size/2);
      children[3].updateSizeAndPosition(size/2, xCoord+size/2, yCoord+size/2);


      //if size is 0 and maxdepth is not 0 exception
 }

 
 /*
  * Returns a List of blocks to be drawn to get a graphical representation of this block.
  * 
  * This includes, for each undivided Block:
  * - one BlockToDraw in the color of the block
  * - another one in the FRAME_COLOR and stroke thickness 3
  * 
  * Note that a stroke thickness equal to 0 indicates that the block should be filled with its color.
  *  
  * The order in which the blocks to draw appear in the list does NOT matter.
  */
 public ArrayList<BlockToDraw> getBlocksToDraw() {

  ArrayList<BlockToDraw> list = new ArrayList<>();
  if (children.length == 0){
      list.add(new BlockToDraw(color, xCoord, yCoord, size, 0));
      list.add(new BlockToDraw(GameColors.FRAME_COLOR, xCoord, yCoord, size, 3));
  }
  else {
    for (int i = 0; i < 4; i++){
      list.addAll(children[i].getBlocksToDraw());
   }
  }

  return list;
 }

 /*
  * This method is provided and you should NOT modify it. 
  */
 public BlockToDraw getHighlightedFrame() {
  return new BlockToDraw(GameColors.HIGHLIGHT_COLOR, this.xCoord, this.yCoord, this.size, 5);
 }
 
 
 
 /*
  * Return the Block within this Block that includes the given location
  * and is at the given level. If the level specified is lower than 
  * the lowest block at the specified location, then return the block 
  * at the location with the closest level value.
  * 
  * The location is specified by its (x, y) coordinates. The lvl indicates 
  * the level of the desired Block. Note that if a Block includes the location
  * (x, y), and that Block is subdivided, then one of its sub-Blocks will 
  * contain the location (x, y) too. This is why we need lvl to identify 
  * which Block should be returned. 
  * 
  * Input validation: 
  * - this.level <= lvl <= maxDepth (if not throw exception)
  * - if (x,y) is not within this Block, return null.
  */
 public Block getSelectedBlock(int x, int y, int lvl) {
  /*
   * ADD YOUR CODE HERE
   */
  if (lvl < level || lvl > maxDepth){
       throw new IllegalArgumentException("Level provided is smaller than this Block’s level or larger than its\n" +
               "maximum depth");
  }
  if (x < xCoord || y < yCoord || x > xCoord+size || y > yCoord+size) return null;

  if (children.length == 0 || lvl == level){ //should you check for if level is smaller than lowest level
    return this;
  }

  if (x >= xCoord + size/2){
    if (y < yCoord + size/2){
     return children[0].getSelectedBlock(x, y, lvl);
    }
    else {
     return children[3].getSelectedBlock(x,y,lvl);
    }
  }
  else {
      if (y < yCoord + size/2){
          return children[1].getSelectedBlock(x,y,lvl);
      }
      else {
          return children[2].getSelectedBlock(x,y,lvl);
      }
     }
 }

 
 

 /*
  * Swaps the child Blocks of this Block. 
  * If input is 1, swap vertically. If 0, swap horizontally. 
  * If this Block has no children, do nothing. The swap 
  * should be propagate, effectively implementing a reflection
  * over the x-axis or over the y-axis.
  * 
  */
 public void reflect(int direction) {

     if (direction != 0 && direction != 1) {
         throw new IllegalArgumentException("Reflection direction should be 1 or 0");
     }
     if (children.length == 0) return;
     for (int i = 0; i<4; i++){
         children[i].reflect(direction);
     }

     if (direction == 0){
//         children[0].yCoord = children[0].yCoord + size/2;
//         children[3].yCoord = children[3].yCoord - size/2;
         children[0].shiftY(size/2);
         children[3].shiftY(-size/2);
         Block tmp = children[0];
         children[0] = children[3];
         children[3] = tmp;
//         children[1].yCoord = children[1].yCoord + size/2;
//         children[2].yCoord = children[2].yCoord - size/2;
         children[1].shiftY(size/2);
         children[2].shiftY(-size/2);

         Block tmp2 = children[1];
         children[1] = children[2];
         children[2] = tmp2;

     }
     else {

         children[1].shiftX(size/2);
         children[0].shiftX(-size/2);
        Block tmp = children[0];
        children[0]  = children[1];
        children[1] = tmp;


         children[2].shiftX(size/2);
         children[3].shiftX(-size/2);
        Block tmp2 = children[2];
        children[2] = children[3];
        children[3] = tmp2;

     }

 }

private void shiftY(int shift){
     yCoord = yCoord + shift;
     if (children.length!=0){
         for (int i = 0; i <4; i++){
             children[i].shiftY(shift);
         }
     }
}
private void shiftX (int shift){
    xCoord = xCoord + shift;
    if (children.length!=0){
        for (int i = 0; i <4; i++){
            children[i].shiftX(shift);
        }
    }
}
 
 /*
  * Rotate this Block and all its descendants. 
  * If the input is 1, rotate clockwise. If 0, rotate 
  * counterclockwise. If this Block has no children, do nothing.
  */
 public void rotate(int direction) {
  /*
   * ADD YOUR CODE HERE
   */
    if (direction != 0 && direction !=1){
        throw new IllegalArgumentException("Rotation direction should be 1 or 0");
    }
    if (children.length == 0) return;

    for (int i = 0; i < 4; i++){
        children[i].rotate(direction);
    }

    if (direction == 0){
        children[0].shiftX(-size/2);
        children[1].shiftY(size/2);
        children[2].shiftX(size/2);
        children[3].shiftY(-size/2);
        Block tmp0  = children[0];
        Block tmp1 = children[1];
        Block tmp2 = children[2];
        Block tmp3 = children[3];
        children[0] = tmp3;
        children[1] = tmp0;
        children[2] = tmp1;
        children[3] = tmp2;
    }
    else {
        children[0].shiftY(size/2);
        children[1].shiftX(size/2);
        children[2].shiftY(-size/2);
        children[3].shiftX(-size/2);
        Block tmp0  = children[0];
        Block tmp1 = children[1];
        Block tmp2 = children[2];
        Block tmp3 = children[3];
        children[0] = tmp1;
        children[1] = tmp2;
        children[2] = tmp3;
        children[3] = tmp0;
    }
 }
 


 /*
  * Smash this Block.
  * 
  * If this Block can be smashed,
  * randomly generate four new children Blocks for it.  
  * (If it already had children Blocks, discard them.)
  * Ensure that the invariants of the Blocks remain satisfied.
  * 
  * A Block can be smashed iff it is not the top-level Block 
  * and it is not already at the level of the maximum depth.
  * 
  * Return True if this Block was smashed and False otherwise.
  * 
  */
 public boolean smash() {
  /*
   * ADD YOUR CODE HERE
   */
     if (level == 0 || level == maxDepth) return false;
     children = new Block[4];

     Block block0 = new Block(level+1, maxDepth);
     block0.updateSizeAndPosition(size/2, xCoord+size/2, yCoord);
     Block block1 = new Block(level+1, maxDepth);
     block1.updateSizeAndPosition(size/2, xCoord, yCoord);
     Block block2 = new Block(level+1, maxDepth);
     block2.updateSizeAndPosition(size/2, xCoord, yCoord+size/2);
     Block block3 = new Block(level+1, maxDepth);
     block3.updateSizeAndPosition(size/2, xCoord+size/2, yCoord+size/2);

     children[0] = block0;
     children[1] = block1;
     children[2] = block2;
     children[3] = block3;
//
//
//     Block block = new Block(level, maxDepth);
//     block.updateSizeAndPosition(size, xCoord, yCoord);
//     if (block.children.length == 0){
//         this.children = new Block[0];
//         this.color = block.color;
//     }
//     else {
//         this.children = new Block[4];
//         for (int i = 0; i < 4; i++){
//             children[i] = block.children[i];
//         }
//     }

  return true;
 }
 
 
 /*
  * Return a two-dimensional array representing this Block as rows and columns of unit cells.
  * 
  * Return and array arr where, arr[i] represents the unit cells in row i, 
  * arr[i][j] is the color of unit cell in row i and column j.
  * 
  * arr[0][0] is the color of the unit cell in the upper left corner of this Block.
  */
 public Color[][] flatten() {
  /*
   * ADD YOUR CODE HERE
   */
//     int sizeTmp;
//     int unit;
//     int cellsPerEdge = (int) Math.pow(2, maxDepth);
//     Color[][] arr = new Color[cellsPerEdge][cellsPerEdge];
//
//     sizeTmp = (int) Math.pow(2, maxDepth);//added
//     Block block = this.deepCopy();
//     block.updateSizeAndPosition(sizeTmp,xCoord,yCoord);//added
//     //this.printBlock();
//     unit = sizeTmp/cellsPerEdge;
//
//
//
//     for (int i = 0; i < cellsPerEdge; i++){
//         for (int j = 0; j < cellsPerEdge; j++){
//             arr[i][j] = block.getSelectedBlock(j*unit, i*unit, maxDepth).color;
//         }
//     }


     int sizeTmp;
     int unit;
     int cellsPerEdge = (int) Math.pow(2, maxDepth);
     Color[][] arr = new Color[cellsPerEdge][cellsPerEdge];
     if (size == 0){
         sizeTmp = (int) Math.pow(2, maxDepth);//added
         this.updateSizeAndPosition(sizeTmp,xCoord,yCoord);//added
         unit = sizeTmp/cellsPerEdge;
     }
     else{
         unit = size/cellsPerEdge;
     }

     for (int i = 0; i < cellsPerEdge; i++){
         for (int j = 0; j < cellsPerEdge; j++){
             arr[i][j] = getSelectedBlock(j*unit, i*unit, maxDepth).color;
         }
     }
     if (size == 0) this.updateSizeAndPosition(size, xCoord,yCoord);//added
    return arr;
 }

 private Block deepCopy(){
     Block b;
     if (children.length == 0){
         b = new Block(xCoord, yCoord, size, level,maxDepth,color, new Block[0]);
     }
     else {
         Block[] tmp = new Block[4];
         for (int i =0; i <4; i++){
             tmp[i] = children[i].deepCopy();
         }
         b = new Block(xCoord, yCoord, size, level,maxDepth,color, tmp);
     }
//     if (children.length != 0) {
//         for (int i = 0; i < 4; i++) {
//             b.children[i] = children[i].deepCopy();
//         }
//     }
     return b;
 }

 
 
 // These two get methods have been provided. Do NOT modify them. 
 public int getMaxDepth() {
  return this.maxDepth;
 }
 
 public int getLevel() {
  return this.level;
 }


 /*
  * The next 5 methods are needed to get a text representation of a block. 
  * You can use them for debugging. You can modify these methods if you wish.
  */
 public String toString() {
  return String.format("pos=(%d,%d), size=%d, level=%d"
    , this.xCoord, this.yCoord, this.size, this.level);
 }

 public void printBlock() {
  this.printBlockIndented(0);
 }

 private void printBlockIndented(int indentation) {
  String indent = "";
  for (int i=0; i<indentation; i++) {
   indent += "\t";
  }

  if (this.children.length == 0) {
   // it's a leaf. Print the color!
   String colorInfo = GameColors.colorToString(this.color) + ", ";
   System.out.println(indent + colorInfo + this);   
  } else {
   System.out.println(indent + this);
   for (Block b : this.children)
    b.printBlockIndented(indentation + 1);
  }
 }
 
 private static void coloredPrint(String message, Color color) {
  System.out.print(GameColors.colorToANSIColor(color));
  System.out.print(message);
  System.out.print(GameColors.colorToANSIColor(Color.WHITE));
 }

 public void printColoredBlock(){
  Color[][] colorArray = this.flatten();
  for (Color[] colors : colorArray) {
   for (Color value : colors) {
    String colorName = GameColors.colorToString(value).toUpperCase();
    if(colorName.length() == 0){
     colorName = "\u2588";
    }else{
     colorName = colorName.substring(0, 1);
    }
    coloredPrint(colorName, value);
   }
   System.out.println();
  }
 }
 
}
