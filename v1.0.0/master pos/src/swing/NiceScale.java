/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

/**
 *
 * @author usER
 */
public class NiceScale {
    private double min;
    private double max;
    private int maxTicks = 11;
    private double tickSpacing;
    private double range;
    private double niceMin;
    private double niceMax;

    public NiceScale(final double min, final double max) {
        this.min = min;
        this.max = max;
        calculate();
    }
    
    private void calculate() {
        range = niceNum(max - min, false);
        tickSpacing = niceNum(range / (maxTicks - 1), true);
        niceMin = Math.floor((min / tickSpacing) * tickSpacing);
        niceMax = Math.ceil((max / tickSpacing) * tickSpacing);
    }
    
    private double niceNum(final double range, final boolean round) {
        double exponent;
        double fraction;
        double niceFracttion;
        
        exponent = Math.floor(Math.log10(range));
        fraction = range / Math.pow(10, exponent);
        
        if(round) {
            if(fraction < 1.5) { 
                niceFracttion = 1;
            } else if(fraction < 3) {
                niceFracttion = 2;
            } else if(fraction < 7) {
                niceFracttion = 5;
            } else {
                niceFracttion = 10;
            }
        } else {
            if(fraction <= 1) {
                niceFracttion = 1;
            } else if(fraction <= 2) {
                niceFracttion = 2;
            } else if(fraction <= 5) {
                niceFracttion = 5;
            } else {
                niceFracttion = 5;
            }
        }
        
        return niceFracttion * Math.pow(10, exponent);
    }
    
    public void setMinMax(final double min, final double max) {
        this.min = min;
        this.max = max;
        calculate();
    }
    
    public void setMaxTicks(int maxTicks) {
        this.maxTicks = maxTicks;
        calculate();
    }
    
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
        calculate();
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
        calculate();
    }

    public int getMaxTicks() {
        return maxTicks;
    }


    public double getTickSpacing() {
        return tickSpacing;
    }

    public void setTickSpacing(double tickSpacing) {
        this.tickSpacing = tickSpacing;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getNiceMin() {
        return niceMin;
    }

    public void setNiceMin(double niceMin) {
        this.niceMin = niceMin;
    }

    public double getNiceMax() {
        return niceMax;
    }

    public void setNiceMax(double niceMax) {
        this.niceMax = niceMax;
    }
}
