/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import action.ActionPagination;
import action.PaginationItemRender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import util.ModelDefaultPaginationItemRender;
import util.ModelPage;

/**
 *
 * @author usER
 */
public class Pagination extends JPanel{

    private PaginationItemRender paginationItemRender;
    private List<ActionPagination> actions = new ArrayList<>();
    private ModelPage page;
    
    public Pagination() {
        initiation();
    }
    
    private void initiation() {
        paginationItemRender = new ModelDefaultPaginationItemRender();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPagination(1, 1);
    }
    
    private void runAction() {
        for(ActionPagination action : actions) {
            action.pageChanged(page.getCurrent());
        }
    }
   
    private boolean isEnable(Object item) {
        return (item instanceof ModelPage.BreakLabel || Integer.valueOf(item.toString()) != page.getCurrent());
    }
    
    public void addActionPagination(ActionPagination action) {
        actions.add(action);
    }
    
    public void setPagination(int current, int totalPage) {
        if(current > totalPage) {
            current = totalPage;
        }
        
        if(page == null || (page.getCurrent() != current || page.getTotalPage() != totalPage)) {
            changePage(current, totalPage);
        }
    }
    
    
    private void changePage(int current, int totalPage) {
        page = paginate(current, totalPage);
        removeAll();
        repaint();
        revalidate();
        
        JButton btnPreviouse = paginationItemRender.createPaginationItem("Previouse", true, false, page.isPreviouse());
        btnPreviouse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page.getCurrent() > 1) {
                    setPagination(page.getCurrent() - 1, totalPage);
                    runAction();
                }
            }
        });
        
        add(btnPreviouse);
        
        for(Object item : page.getItems()) {
            JButton btnPage = paginationItemRender.createPaginationItem(item, false, false, isEnable(item));
            if(item instanceof Integer) {
                if(Integer.valueOf(item.toString()) == page.getCurrent()) {
                btnPage.setSelected(true);
                    
                }
            }
            
            btnPage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!btnPage.isSelected() && item != null) {
                        if(item instanceof ModelPage.BreakLabel) {
                           ModelPage.BreakLabel mp = (ModelPage.BreakLabel) item;
                            setPagination(mp.getPage(), totalPage);
                        }
                        
                        else {
                            setPagination(Integer.valueOf(item.toString()), totalPage);
                        }
                        
                        runAction();
                        
                    }
                }
            });
            
            add(btnPage);
        }
        
        JButton btnNext = paginationItemRender.createPaginationItem("Next", false, true, page.isNext());
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page.getCurrent() < page.getTotalPage()) {
                    setPagination(page.getCurrent() + 1, totalPage);
                    runAction();
                }
            }
        });
        
        add(btnNext);
    }
    
    
    private ModelPage paginate(int current, int max) {
        boolean previouse = current > 1;
        boolean next = current < max;
        
        List<Object> items = new ArrayList<>();
        items.add(1);
        
        if(current == 1 && max == 1) {
            ModelPage modelPage = new ModelPage(current, previouse, next, items, max);
            return modelPage;
        }
        
        int a = 2;
        int a1 = current - a;
        int a2 = current + a;
        
        if(current > 4) {
            items.add(new ModelPage.BreakLabel((a1 > 2? a1 : 2) - 1));
        }
        
        for(int i = a1 > 2? a1 : 2; i <= Math.min(max, a2); i++) {
            items.add(i);
        }
        
        if(a2 + 1 < max) {
            items.add(new ModelPage.BreakLabel(Integer.valueOf(items.get(items.size() - 1).toString()) + 1));
        }
        
        if(a2 < max) {
            items.add(max);
        }
        
        return new ModelPage(current, previouse, next, items, max);
    }
}
