/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package action;

import java.awt.Graphics2D;
import swing.BlankPlotChart;
import swing.SeriesSize;

/**
 *
 * @author usER
 */
public abstract class BlankPlotChatRender {
    public abstract String getLabelText(int index);
    public abstract  void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index);
}
