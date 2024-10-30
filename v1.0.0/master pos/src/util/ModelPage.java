/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.List;

/**
 *
 * @author usER
 */
public class ModelPage {

    public ModelPage(int current, boolean previouse, boolean next, List<Object> items, int totalPage) {
        this.current = current;
        this.previouse = previouse;
        this.next = next;
        this.items = items;
        this.totalPage = totalPage;
    }

    public ModelPage() {
        
    }

    private int current;
    private boolean previouse;
    private boolean next;
    private List<Object> items;
    private int totalPage;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isPreviouse() {
        return previouse;
    }

    public void setPreviouse(boolean previouse) {
        this.previouse = previouse;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    
    @Override
    public String toString() {
        return "current: " + current + "\n" + previouse + " " + items.toString() + " " + next;
    }
    
    public static class BreakLabel{

        public BreakLabel(int page) {
            this.page = page;
        }

        public BreakLabel() {
        
        }

        
        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
        private int page;
        
        @Override
        public String toString() {
            return "...";
        }
     }
}
