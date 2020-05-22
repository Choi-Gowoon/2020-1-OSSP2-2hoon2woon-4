package org.psnbtech.Items;

import org.psnbtech.BoardPanel;
import org.psnbtech.Tetris;

import java.util.Random;
import java.util.Vector;

public class ItemManager {
    private static int ITEM_NUM = 6;
    private Vector<ItemType> items = new Vector<>();
    private int itemTerm;
    private Tetris tetris;
    private BoardPanel board;

    public ItemManager(Tetris tetris, BoardPanel board){
        this.itemTerm = 0;
        this.tetris = tetris;
        this.board = board;
    }

    public Vector<ItemType> getItems() {
        return items;
    }

    public BoardPanel getBoard() {
        return board;
    }

    public Tetris getTetris() {
        return tetris;
    }

    class Point{
        int x;
        int y;
        Point(){
        }
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    // TODO ! using board;
    public Point itemLocation(){
        Point location = new Point();
        Vector<Point> list = new Vector<>();
        for(int row = 0; row < board.ROW_COUNT; row++){
            for(int col = 0; col < board.COL_COUNT; col++){
                if(board.getTile(col, row)!=null){
                    list.add(new Point(col, row));
                }
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(list.size()-1);
        location.x = list.get(randomIndex).x;
        location.y = list.get(randomIndex).y;
        return location;
    }

    public void generateItem(){
        if(itemTerm == 5){
            itemTerm = 0;
            Point location = itemLocation();
            Random random = new Random();
            int index = random.nextInt(ITEM_NUM-1) + 1;
            switch (index){
                case 1:
                    items.add(new Item_AddOneLine(location.x,location.y, this));
                    break;
                case 2:
                    items.add(new Item_DeleteAllBlocks(location.x,location.y, this));
                    break;
                case 3:
                    items.add(new Item_DeleteSomeLine(location.x,location.y, this));
                    break;
                case 4:
                    items.add(new Item_DisableRotate(location.x,location.y, this));
                    break;
                case 5:
                    items.add(new Item_DoubleScore(location.x,location.y, this));
                    break;
                case 6:
                    items.add(new Item_ReverseKey(location.x,location.y,this));
                    break;
            }
        }
        else{
            itemTerm ++;
        }
    }

    public void deleteItem(int row){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).y == row){
                items.get(i).action();
                items.remove(i);
            }
        }
    }

    public void manageBadItem(){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).isBad){
                items.get(i).count++;
            }
            if(items.get(i).count == 7){
                items.remove(i);
            }
        }
    }

    public void clear(){
        itemTerm = 0;
        items.clear();
    }
}
