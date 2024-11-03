package assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBlock {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
	System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
	System.setOut(originalOut);
    }

    @Test
    public void testConstructor1() {
	Block.gen.setSeed(2);
	Block blockDepth2 = new Block(0, 2);
	blockDepth2.printBlock();
	assertEquals("""
                pos=(0,0), size=0, level=0
                \tGREEN, pos=(0,0), size=0, level=1
                \tRED, pos=(0,0), size=0, level=1
                \tYELLOW, pos=(0,0), size=0, level=1
                \tpos=(0,0), size=0, level=1
                \t\tBLUE, pos=(0,0), size=0, level=2
                \t\tRED, pos=(0,0), size=0, level=2
                \t\tYELLOW, pos=(0,0), size=0, level=2
                \t\tBLUE, pos=(0,0), size=0, level=2""", outContent.toString().replace("\r", "").trim());
    }

    @Test
    public void testUpdateSizeAndPosition() {
	Block.gen.setSeed(2);
	Block blockDepth2 = new Block(0, 2);
	blockDepth2.updateSizeAndPosition(16, 0, 0);
	blockDepth2.printBlock();
	assertEquals("""
                pos=(0,0), size=16, level=0
                \tGREEN, pos=(8,0), size=8, level=1
                \tRED, pos=(0,0), size=8, level=1
                \tYELLOW, pos=(0,8), size=8, level=1
                \tpos=(8,8), size=8, level=1
                \t\tBLUE, pos=(12,8), size=4, level=2
                \t\tRED, pos=(8,8), size=4, level=2
                \t\tYELLOW, pos=(8,12), size=4, level=2
                \t\tBLUE, pos=(12,12), size=4, level=2""", outContent.toString().replace("\r", "").trim());
    }

    @Test
    public void testGetBlocksToDraw() {
	Block.gen.setSeed(2);
	Block blockDepth2 = new Block(0, 2);
	blockDepth2.updateSizeAndPosition(16, 0, 0);
	HashSet<BlockToDraw> set1 = new HashSet<>(blockDepth2.getBlocksToDraw());
	HashSet<BlockToDraw> set2 = new HashSet<>(List.of(
		new BlockToDraw(GameColors.RED, 0, 0, 8, 0),
		new BlockToDraw(GameColors.FRAME_COLOR, 0, 0, 8, 3),
		new BlockToDraw(GameColors.GREEN, 8, 0, 8, 0),
		new BlockToDraw(GameColors.FRAME_COLOR, 8, 0, 8, 3),
		new BlockToDraw(GameColors.YELLOW, 0, 8, 8, 0),
		new BlockToDraw(GameColors.FRAME_COLOR, 0, 8, 8, 3),

		new BlockToDraw(GameColors.RED, 8, 8, 4, 0),
		new BlockToDraw(GameColors.BLUE, 12, 8, 4, 0),
		new BlockToDraw(GameColors.YELLOW, 8, 12, 4, 0),
		new BlockToDraw(GameColors.BLUE, 12, 12, 4, 0),
		new BlockToDraw(GameColors.FRAME_COLOR, 8, 8, 4, 3),
		new BlockToDraw(GameColors.FRAME_COLOR, 12, 8, 4, 3),
		new BlockToDraw(GameColors.FRAME_COLOR, 8, 12, 4, 3),
		new BlockToDraw(GameColors.FRAME_COLOR, 12, 12, 4, 3)
	));
	assertEquals(set1.stream().map(s -> s.getColor().toString() + s.getShape() + s.getStrokeThickness()).collect(Collectors.toSet()),
		set2.stream().map(s -> s.getColor().toString() + s.getShape() + s.getStrokeThickness()).collect(Collectors.toSet()));
    }

    @Test
    public void testGetSelectedBlock() {
	Block.gen.setSeed(4);
	Block blockDepth3 = new Block(0, 3);
	blockDepth3.updateSizeAndPosition(16, 0, 0);
	Block b1 = blockDepth3.getSelectedBlock(2, 15, 1);
	b1.printBlock();
	assertEquals("RED, pos=(0,8), size=8, level=1", outContent.toString().replace("\r", "").trim());
	outContent.reset();
	b1 = blockDepth3.getSelectedBlock(3, 5, 2);
	b1.printBlock();
	assertEquals("""
                pos=(0,4), size=4, level=2
                \tYELLOW, pos=(2,4), size=2, level=3
                \tGREEN, pos=(0,4), size=2, level=3
                \tYELLOW, pos=(0,6), size=2, level=3
                \tBLUE, pos=(2,6), size=2, level=3""", outContent.toString().replace("\r", "").trim());
    }

    @Test
    public void testFlatten() {
	Block.gen.setSeed(2);
	Block block = new Block(0, 2);
	//block.updateSizeAndPosition(16,0,0);
	block.printColoredBlock();
	assertEquals("""
                RRGG
                RRGG
                YYRB
                YYYB""", outContent.toString().replace("\r", "").replaceAll("\u001B\\[\\d+m", "").trim());
    }
}